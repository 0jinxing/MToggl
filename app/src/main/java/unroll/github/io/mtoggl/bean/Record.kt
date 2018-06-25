package unroll.github.io.mtoggl.bean

import unroll.github.io.mtoggl.bean.base.BaseBean

class Record : BaseBean {
    var id: Long? = null
    var tagId: Long? = null
    var description: String? = null
    var ticket:Long? = null

    constructor(){}
    constructor(tagId: Long?, description: String?) : super() {
        this.tagId = tagId
        this.description = description
    }

    constructor(tagId: Long?, description: String?, ticket: Long?) : super() {
        this.tagId = tagId
        this.description = description
        this.ticket = ticket
    }

}