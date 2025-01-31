//[OpenStackGateway](../../index.md)/[dk.aau.claaudia.openstackgateway.extensions](index.md)

# Package dk.aau.claaudia.openstackgateway.extensions

## Types

| Name | Summary |
|---|---|
| [ExtensionConfig](-extension-config/index.md) | [jvm]<br>@Component<br>class [ExtensionConfig](-extension-config/index.md)(openStackProperties: [OpenStackProperties](../dk.aau.claaudia.openstackgateway.config/-open-stack-properties/index.md))<br>Allow extension functions to use config |

## Functions

| Name | Summary |
|---|---|
| [getLogger](get-logger.md) | [jvm]<br>inline fun [getLogger](get-logger.md)(): Logger |

## Properties

| Name | Summary |
|---|---|
| [openstackName](openstack-name.md) | [jvm]<br>val Job.[openstackName](openstack-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Given a ucloud job, add prefix to id to get openstack name |
| [ucloudId](ucloud-id.md) | [jvm]<br>val Stack.[ucloudId](ucloud-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Given a stack, remove the name prefix to get ucloud id |
