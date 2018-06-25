package unroll.github.io.mtoggl.bean

import unroll.github.io.mtoggl.bean.base.BaseBean

class Tag : BaseBean {
    var id: Long? = null
    var name: String? = null
    var color: String? = null

    constructor() {}
    constructor(name: String?, color: String?) : super() {
        this.name = name
        this.color = color
    }
}