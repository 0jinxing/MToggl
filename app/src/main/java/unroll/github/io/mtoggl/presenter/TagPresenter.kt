package unroll.github.io.mtoggl.presenter

import org.litepal.LitePal
import unroll.github.io.mtoggl.bean.Tag

object TagPresenter {

    public fun getTicket(tag: Tag): Long {
        var ticket = 0L
        for (record in RecordPresenter.getRecordListByTag(tag)) {
            ticket += record.ticket!!
        }
        return ticket
    }

    public fun getTagAll(): List<Tag> {
        return LitePal.findAll(Tag::class.java)
    }

    public fun remove(tag: Tag) {
        RecordPresenter.remove(tag)
        tag.delete()
    }
}