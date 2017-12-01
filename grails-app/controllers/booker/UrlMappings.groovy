package booker

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        get "/books" (controller: "book", action: "list")
        post "/book" (controller: "book", action: "create")
        put "/book/$id" (controller: "book", action: "update")
        get "/book/$id" (controller: "book", action: "get")
        delete "/book/$id" (controller: "book", action: "delete")

        get "/dbs/" (controller: "DB", action: "index")
        post "/db/$dbName" (controller: "DB", action: "create")
        delete "/db/$dbName" (controller: "DB", action: "delete")

        post "/log/$message"(controller: "logTesting", action: "log")

        "/apidoc/$action?/$id?"(controller: "apiDoc", action: "getDocuments")

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
