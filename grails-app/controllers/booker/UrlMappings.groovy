package booker

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        get "/books" (controller: "book", action: "list")
        post "/book/" (controller: "book", action: "create")
        get "/book/$id" (controller: "book", action: "get")
        delete "/book/$id" (controller: "book", action: "delete")

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
