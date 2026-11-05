package plugins.kommonsVersions.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

@CacheableTask
abstract class GenerateIosConfigTask : DefaultTask() {

    @get:Input
    abstract val files: MapProperty<String, String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun generate() {
        val directory = outputDirectory.get().asFile

        if (!directory.exists()) {
            directory.mkdirs()
        }

        files.get().forEach { (fileName, fileContent) ->
            val outputFile = directory.resolve(fileName)

            if (outputFile.readTextOrNull() != fileContent) {
                outputFile.writeText(fileContent)

                logger.lifecycle("--------------------------------------------------")
                logger.lifecycle("iOS Config Updated -> ${outputFile.name}")
                logger.lifecycle("Path: ${outputFile.absolutePath}")
            }
        }
    }

    private fun java.io.File.readTextOrNull(): String? =
        if (exists()) readText() else null
}