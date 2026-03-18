// package com.supermarket

// import io.ktor.server.application.*
// import io.ktor.server.response.*
// import io.ktor.http.*
// import io.pebbletemplates.pebble.PebbleEngine
// import java.io.StringWriter

// suspend fun Applicationcall.respond(, templateName: String, context: Map<String, Any?> = emptyMap()) {
//     val writer = StringWriter()
//     val template = pebbleEngine.getTemplate(templateName)
//     template.evaluate(writer, context)

//     respondText(writer.toString(), ContentType.Text.Html)
// }