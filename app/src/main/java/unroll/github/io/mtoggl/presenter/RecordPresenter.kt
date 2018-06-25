package unroll.github.io.mtoggl.presenter

import org.litepal.LitePal
import unroll.github.io.mtoggl.bean.Record
import unroll.github.io.mtoggl.bean.Tag

object RecordPresenter {

    fun add(record: Record) {
        record.save()
    }

    fun add(description: String, tagName: String, ticket: Long): Record {
        val curTagName = if (tagName.isNullOrEmpty()) "default" else tagName
        var tag = LitePal.where("name = ?", curTagName.trim()).findFirst(Tag::class.java)
        if (tag == null) {
            tag = Tag(curTagName, ColorPresenter.getRandomColor())
            tag.save()
        }
        val record = Record(tag.id, if (description.isNullOrEmpty()) "no description" else description, ticket)
        record.save()
        return record
    }

    fun getRecordAll(): List<Record> {
        return LitePal.findAll(Record::class.java)
    }

    fun getRecordListByTag(tag: Tag): List<Record> {
        return LitePal.where("tagId = ?", tag.id.toString()).find(Record::class.java).toList()
    }

    fun getTag(record: Record): Tag? {
        return LitePal.where("id = ?", record.tagId.toString()).findFirst(Tag::class.java)
    }

    fun remove(record: Record) {
        record.delete()
    }

    fun remove(tag:Tag) {
        for(record in LitePal.where("tagId = ?", tag.id.toString()).find(Record::class.java)) {
            record.delete()
        }
    }
}
