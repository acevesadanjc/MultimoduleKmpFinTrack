/** Se configura plugin GMS o HMS **/
val requestedTasks = gradle.startParameter.taskNames
logger.lifecycle("Task executed: ${requestedTasks.joinToString(", ").ifEmpty { "CompileTask" }}")

val normalizedTasks = requestedTasks.map { it.lowercase() }
val aggregateTasks = normalizedTasks.filter {
    it.substringAfterLast(':') in setOf("build", "assemble", "bundle")
}
if (aggregateTasks.isNotEmpty()) {
    throw GradleException(
        "Provider builds must target one explicit variant, for example " +
                ":androidApp:assembleEcuadorHmsDebug."
    )
}

val variantIds = listOf(
    "ecuadorgms",
    "ecuadorhms",
    "cenamgms",
    "cenamhms"
)
val requestedVariants = variantIds.filter { variant ->
    normalizedTasks.any { it.contains(variant) }
}
if (requestedVariants.size > 1) {
    throw GradleException(
        "Build one market/provider variant per invocation; requested: " +
                requestedVariants.joinToString()
    )
}

val providerWasRequested = normalizedTasks.any {
    it.contains("gms") || it.contains("hms")
}
val selectedVariant = requestedVariants.singleOrNull()

if (selectedVariant == null && providerWasRequested) {
    throw GradleException(
        "Could not identify one explicit market/provider variant. " +
                "Use a task such as :androidApp:assembleEcuadorHmsDebug."
    )
}

if (selectedVariant != null) {
    if (selectedVariant.endsWith("hms")) {
        val market = if (selectedVariant.startsWith("ecuador")) "ecuador" else "cenam"
        val sourceConfigFile = layout.projectDirectory.file(
            "src/$market/agconnect-services.json"
        ).asFile
        val stagedConfigFile =
            layout.projectDirectory.file("agconnect-services.json").asFile

        if (!sourceConfigFile.isFile) {
            throw GradleException(
                "Missing AGConnect config for '$market': $sourceConfigFile"
            )
        }

        // Bootstrap: AGConnect must see the JSON before it configures its tasks.
        sourceConfigFile.copyTo(stagedConfigFile, overwrite = true)

        val stageAgconnectConfig = tasks.register("stageAgconnectConfig") {
            inputs.file(sourceConfigFile)
            outputs.file(stagedConfigFile)

            doLast {
                sourceConfigFile.copyTo(stagedConfigFile, overwrite = true)
            }
        }

        logger.lifecycle("Applying Plugin: Huawei AGConnect for $market")
        apply(plugin = "com.huawei.agconnect")

        val agconnectTaskPrefix =
            "process${market.replaceFirstChar { it.uppercase() }}Hms"

        tasks.configureEach {
            if (name.startsWith(agconnectTaskPrefix) && name.endsWith("AGCPlugin")) {
                dependsOn(stageAgconnectConfig)
            }
        }
    } else {
        logger.lifecycle("Applying Plugin: Google Services")
        apply(plugin = "com.google.gms.google-services")
    }
}