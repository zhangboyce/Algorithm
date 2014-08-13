package data;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import common.json.JsonBuilderExecutor;
import common.utils.SegmenterUtils;
import data.db.DBCollectionCreator;
import data.entity.Comment;
import data.entity.Product;
import data.excel.ExcelCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boyce on 2014/7/7.
 */
public class Comment2Excel {
    public static void main(String[] args) {
        try {
            DBCollection dbCollection = DBCollectionCreator.newDBCollection("comment");
            BasicDBObject columns = new BasicDBObject();
            columns.put("_id", 1);
            columns.put("Content", 1);
           DBCursor dbCursor = dbCollection.find(new BasicDBObject(), columns).limit(10000);

            List<Comment> comments = new ArrayList<Comment>();
            while (dbCursor.hasNext()) {
                DBObject it = dbCursor.next();
                Comment comment = JsonBuilderExecutor.getInstance().toObject(it.toString(), Comment.class);
                comments.add(comment);

                System.out.println(comment);
            }

            List<Comment> newComments = new ArrayList<Comment>();
            Comment segComment = null;
            for (Comment comment: comments) {
                newComments.add(comment);
                segComment = new Comment();
                segComment.setContent(SegmenterUtils.IKSEG.segment(comment.getContent().toString()));
                newComments.add(segComment);
            }

            ExcelCreator excel =
                    new ExcelCreator(new ExcelCreator.ExcelDataInfo<Comment>("comment_"+ System.currentTimeMillis() +".xls", newComments, Comment.class));
            excel.create();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
