tasks.register("deployComponentsKmpArkive") {
    group = "arkive"
    description = "Ejecuta el flujo completo: Graba snapshots, genera metadatos y ensambla la web de Arkive."

    // 1. Definimos las dependencias (qué tareas debe correr)
    dependsOn(":core:presentation:design:recordRoborazziAndroidHostTest")
    dependsOn(":core:presentation:design:generateShowcaseAndroidMain")
    dependsOn("generateWebShowcase")

    // 2. Forzamos el orden secuencial
    // (Asegura que primero grabe, luego genere datos y al final la web)
    tasks.findByName("generateShowcaseAndroidMain")?.mustRunAfter(":core:presentation:design:recordRoborazziAndroidHostTest")
    tasks.findByName("generateWebShowcase")?.mustRunAfter(":core:presentation:design:generateShowcaseAndroidMain")

    // 3. Al finalizar, ofrecemos levantar el servidor automáticamente
    finalizedBy("serveArkive")
}

tasks.register<Exec>("serveArkive") {
    group = "arkive"
    description = "Levanta un servidor local de Python para visualizar el catálogo de Arkive."

    // Obtenemos la ruta absoluta de forma segura
    val showcaseDir = layout.buildDirectory.dir("generated/arkive/showcase").get().asFile

    workingDir = showcaseDir

    // Comando de python
    commandLine("python", "-m", "http.server", "8000")

    doFirst {
        if (!showcaseDir.exists()) {
            throw GradleException("❌ No se encontró el catálogo en: ${showcaseDir.absolutePath}. Ejecuta 'generateWebShowcase' primero.")
        }
        println("\n==========================================================")
        println("🚀 Arkive Web Server")
        println("📂 Ruta: ${showcaseDir.absolutePath}")
        println("🔗 URL:  http://localhost:8000")
        println("✅ El catálogo ya está disponible.")
        println("🛑 Para detener el servidor, presiona [Ctrl + f2] en esta terminal.")
        println("==========================================================\n")
    }
}