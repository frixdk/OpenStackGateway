package dk.aau.claaudia.openstackgateway.controllers

import dk.aau.claaudia.openstackgateway.config.ProviderProperties
import dk.aau.claaudia.openstackgateway.config.UCloudProperties
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.sdu.cloud.CommonErrorMessage
import dk.sdu.cloud.FindByStringId
import dk.sdu.cloud.accounting.api.ProductReference
import dk.sdu.cloud.app.orchestrator.api.*
import dk.sdu.cloud.calls.BulkRequest
import dk.sdu.cloud.calls.BulkResponse
import dk.sdu.cloud.provider.api.UpdatedAclWithResource
import dk.sdu.cloud.providers.JobsController
import dk.sdu.cloud.providers.UCloudClient
import dk.sdu.cloud.providers.UCloudWsContext
import dk.sdu.cloud.providers.UCloudWsDispatcher
import dk.sdu.cloud.service.Loggable
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.RestController

/**
 * This class implements Uclouds JobController from the provider integration package
 */
@RestController
@SecurityRequirement(name = "bearer-key") // Info for openapi interface
class SimpleCompute(
    private val client: UCloudClient,
    private val openstackService: OpenStackService,
    wsDispatcher: UCloudWsDispatcher,
    private val providerProperties: ProviderProperties,
    private val uCloudProperties: UCloudProperties,
) : JobsController(providerProperties.id, wsDispatcher) {
    init {
        log.info("Simple compute init")
    }

    /**
     * Create stacks in openstack from ucloud job.
     * Start each stack creation in openstack asynchronously with createStacks.
     * Then start a task that monitors the stacks in openstack.
     * When they start or report an error this message and status should be sent to ucloud.
     */
    override fun create(request: BulkRequest<Job>): BulkResponse<FindByStringId> {
        log.info("Creating stacks: $request")
        openstackService.createStacks(request.items)

        log.info("Waiting for stacks to start: $request")
        openstackService.asyncMonitorCreations(request.items)

        // FIXME What ids to return here?
        return BulkResponse(listOf())
    }

    /**
     * This currently has no use for Virtual Machines and does nothing.
     */
    override fun extend(request: BulkRequest<JobsProviderExtendRequestItem>): BulkResponse<Unit> {
        TODO("Not yet implemented")
    }

    override fun follow(
        request: JobsProviderFollowRequest,
        wsContext: UCloudWsContext<JobsProviderFollowRequest, JobsProviderFollowResponse, CommonErrorMessage>
    ) {
        TODO("Not yet implemented")
    }

    override fun openInteractiveSession(request: BulkRequest<JobsProviderOpenInteractiveSessionRequestItem>): BulkResponse<OpenSession> {
        TODO("Not yet implemented")
    }

    /**
     * Provide ucloud with the available products.
     * These are basically equivalent to openstack flavors but need to adhere to the ucloud format
     * This includes information additional information, e.g., product is Virtual Machine
     * The UCloud category is translated to availability_zone on the flavor in openstack.
     * All information about ComputeSupport is also hardcoded for now
     */
    override fun retrieveProducts(request: Unit): BulkResponse<ComputeSupport> {
        log.info("Retrieving products")

        val response = BulkResponse(
            openstackService.listFlavors().filterNotNull().map { flavor ->
                ComputeSupport(
                    ProductReference(
                        flavor.name,
                        openstackService.getFlavorExtraSpecs(flavor.id).getOrDefault(
                            "availability_zone", providerProperties.defaultProductCategory
                        ),
                        providerProperties.id
                    ),
                    ComputeSupport.Docker(
                        enabled = false,
                        web = false,
                        vnc = false,
                        logs = false,
                        terminal = false,
                        peers = false,
                        timeExtension = false,
                        utilization = false
                    ),
                    ComputeSupport.VirtualMachine(
                        enabled = true,
                        logs = false,
                        vnc = false,
                        terminal = false,
                        suspension = false,
                        timeExtension = false,
                        utilization = false
                    )
                )
            }
        )

        logger().info(response.toString())
        return response
    }

    /**
     * Unused for now
     */
    override fun retrieveUtilization(request: Unit): JobsRetrieveUtilizationResponse {
        TODO("Not yet implemented")
    }

    /**
     * Ucloud plans to implement this funtionality for VMs
     */
    override fun suspend(request: BulkRequest<Job>) {
        log.info("Suspending jobs: $request")
        openstackService.suspendJobs(request.items)

        log.info("Waiting for stacks to be suspended: $request")
        openstackService.asyncMonitorStackSuspensions(request.items)
    }

    /**
     * Ucloud plans to implement this funtionality for VMs
     */
    // TODO Add override when UCloud adds suspend to library
    //override fun resume(request: BulkRequest<Job>) {
    fun resume(request: BulkRequest<Job>) {
        log.info("Suspending jobs: $request")
        openstackService.resumeJobs(request.items)

        log.info("Waiting for stacks to be resumed: $request")
        openstackService.asyncMonitorStackResumes(request.items)
    }

    /**
     * Delete specific stacks in openstack.
     * It is important to make sure the ucloud jobs are charged before beeing deleted.
     * The function chargeDeleteJobs will first send a charge request to ucloud
     * and the start the deletion process.
     * When an async task has been started for each job,
     * then start a task that monitors the stacks in openstack.
     * When they can no longer be found we assume deletion and status should be sent to ucloud.
     */
    override fun terminate(request: BulkRequest<Job>): BulkResponse<Unit> {
        log.info("Charging and deleting jobs: $request")
        openstackService.chargeDeleteJobs(request.items)

        log.info("Waiting for stacks to be deleted: $request")
        openstackService.asyncMonitorDeletions(request.items)

        //FIXME Okay to return empty list here?
        return BulkResponse(listOf())
    }

    override fun updateAcl(request: BulkRequest<UpdatedAclWithResource<Job>>): BulkResponse<Unit> {
        TODO("Not yet implemented")
    }

    /**
     * Verify that ucloud has the corrent status of the provided jobs/stacks
     */
    override fun verify(request: BulkRequest<Job>) {
        log.info("verify jobs request received. size: ${request.items.size}")
        openstackService.verifyJobs(request.items)
    }

    companion object : Loggable {
        override val log = logger()
    }
}
