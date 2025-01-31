//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.controllers](../index.md)/[SimpleCompute](index.md)

# SimpleCompute

[jvm]\
@RestController

class [SimpleCompute](index.md)(client: UCloudClient, openstackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md), wsDispatcher: UCloudWsDispatcher, provider: [ProviderProperties](../../dk.aau.claaudia.openstackgateway.config/-provider-properties/index.md)) : JobsController

This class implements Uclouds JobController from the provider integration package

## Constructors

| | |
|---|---|
| [SimpleCompute](-simple-compute.md) | [jvm]<br>fun [SimpleCompute](-simple-compute.md)(client: UCloudClient, openstackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md), wsDispatcher: UCloudWsDispatcher, provider: [ProviderProperties](../../dk.aau.claaudia.openstackgateway.config/-provider-properties/index.md)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) : Loggable |

## Functions

| Name | Summary |
|---|---|
| [canHandleWebSocketCall](index.md#-806668799%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [canHandleWebSocketCall](index.md#-806668799%2FFunctions%2F-1216412040)(call: CallDescription&lt;*, *, *&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [create](create.md) | [jvm]<br>open override fun [create](create.md)(request: BulkRequest&lt;Job&gt;)<br>Create stacks in openstack from ucloud job. Start each stack creation in openstack asynchronously with createStacks. Then start a task that monitors the stacks in openstack. When they start or report an error this message and status should be sent to ucloud. |
| [delete](delete.md) | [jvm]<br>open override fun [delete](delete.md)(request: BulkRequest&lt;Job&gt;)<br>Delete specific stacks in openstack. It is important to make sure the ucloud jobs are charged before beeing deleted. The function chargeDeleteJobs will first send a charge request to ucloud and the start the deletion process. When an async task has been started for each job, then start a task that monitors the stacks in openstack. When they can no longer be found we assume deletion and status should be sent to ucloud. |
| [dispatchToHandler](index.md#2087156498%2FFunctions%2F-1216412040) | [jvm]<br>open override fun &lt;[R](index.md#2087156498%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [S](index.md#2087156498%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [E](index.md#2087156498%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [dispatchToHandler](index.md#2087156498%2FFunctions%2F-1216412040)(call: CallDescription&lt;[R](index.md#2087156498%2FFunctions%2F-1216412040), [S](index.md#2087156498%2FFunctions%2F-1216412040), [E](index.md#2087156498%2FFunctions%2F-1216412040)&gt;, request: [R](index.md#2087156498%2FFunctions%2F-1216412040), rawRequest: HttpServletRequest, rawResponse: HttpServletResponse): [S](index.md#2087156498%2FFunctions%2F-1216412040) |
| [dispatchToWebSocketHandler](index.md#-976524103%2FFunctions%2F-1216412040) | [jvm]<br>open override fun &lt;[R](index.md#-976524103%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [S](index.md#-976524103%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [E](index.md#-976524103%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [dispatchToWebSocketHandler](index.md#-976524103%2FFunctions%2F-1216412040)(ctx: UCloudWsContext&lt;[R](index.md#-976524103%2FFunctions%2F-1216412040), [S](index.md#-976524103%2FFunctions%2F-1216412040), [E](index.md#-976524103%2FFunctions%2F-1216412040)&gt;, request: [R](index.md#-976524103%2FFunctions%2F-1216412040)) |
| [extend](extend.md) | [jvm]<br>open override fun [extend](extend.md)(request: BulkRequest&lt;JobsProviderExtendRequestItem&gt;)<br>This currently has no use for Virtual Machines and does nothing. |
| [follow](follow.md) | [jvm]<br>open override fun [follow](follow.md)(request: JobsProviderFollowRequest, wsContext: UCloudWsContext&lt;JobsProviderFollowRequest, JobsProviderFollowResponse, CommonErrorMessage&gt;) |
| [handler](index.md#1373748360%2FFunctions%2F-1216412040) | [jvm]<br>fun [handler](index.md#1373748360%2FFunctions%2F-1216412040)(request: HttpServletRequest, response: HttpServletResponse): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [openInteractiveSession](open-interactive-session.md) | [jvm]<br>open override fun [openInteractiveSession](open-interactive-session.md)(request: BulkRequest&lt;JobsProviderOpenInteractiveSessionRequestItem&gt;): JobsProviderOpenInteractiveSessionResponse |
| [retrieveProducts](retrieve-products.md) | [jvm]<br>open override fun [retrieveProducts](retrieve-products.md)(request: [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): JobsProviderRetrieveProductsResponse<br>Provide ucloud with the available products. These are basically equivalent to openstack flavors but need to adhere to the ucloud format This includes information additonal information, e.g., product is Virtual Machine |
| [retrieveUtilization](retrieve-utilization.md) | [jvm]<br>open override fun [retrieveUtilization](retrieve-utilization.md)(request: [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): JobsProviderUtilizationResponse<br>Unused for now |
| [suspend](suspend.md) | [jvm]<br>open override fun [suspend](suspend.md)(request: BulkRequest&lt;Job&gt;)<br>Unused for now. Ucloud plans to implement this and we need to decide how to suspend stacks in openstack |
| [verify](verify.md) | [jvm]<br>open override fun [verify](verify.md)(request: BulkRequest&lt;Job&gt;)<br>Verify that ucloud has the corrent status of the provided jobs/stacks |
