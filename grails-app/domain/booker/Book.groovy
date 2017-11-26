package booker

import io.swagger.annotations.*

@ApiModel(subTypes = java.lang.Object)
class Book {

    @ApiModelProperty(position = 1, required = true, value = "Unique id of the book")
    String id

    @ApiModelProperty(position = 2, required = true, value = "title of the book, must be provided")
    String title

    @ApiModelProperty(position = 3, required = false, value = "Author of the book")
    String author

    @ApiModelProperty(position = 4, required = false, value = "Ratings out of 5 for the book.")
    int ratings

    @ApiModelProperty(position = 5, required = true, value = "Version of the books")
    String rev

    static constraints = {}

    Map asMap() {
        Map properties = this.getProperties()
        Map nonNullProperties = properties.findAll  {it.value != null}
        return  nonNullProperties
    }
}
