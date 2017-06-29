//package lol.mifan.myblog.util.webmagic;
//
//import lol.mifan.myblog.model.Article;
//import lol.mifan.myblog.model.Tag;
//import lol.mifan.myblog.util.HttpException;
//import lol.mifan.myblog.service.ArticleService;
//import org.springframework.stereotype.Component;
//import us.codecraft.webmagic.ResultItems;
//import us.codecraft.webmagic.Task;
//import us.codecraft.webmagic.pipeline.Pipeline;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Set;
//
///**
// * Created by 米饭 on 2017-04-23.
// */
//@Component
//public class DatabasePipeline implements Pipeline {
//
//    @Resource
//    private ArticleService articleService;
//
//
//    @Override
//    public void process(ResultItems resultItems, Task task) {
//        System.out.println("get page: " + resultItems.getRequest().getUrl());
//
//        Article article = new Article();
//        article.setTitle(resultItems.<String>get("title"));
//        article.setContent(resultItems.<String>get("content"));
//        article.setTitleImg(resultItems.<String>get("title_img"));
//        article.setOutline(resultItems.<String>get("outline"));
//        article.setReprintedFrom(resultItems.getRequest().getUrl());
//
//
//        Set<Tag> tags = article.getTags();
//        List<String> tagList = resultItems.get("tags");
//
//        for (String tagStr: tagList) {
//            tags.add(new Tag(tagStr, false, null, null));
//        }
//
//
////        .add(new Tag(resultItems.get("tag")));
//
//
//
//        try {
//            articleService.add(article);
//        } catch (HttpException e) {
//            e.printStackTrace();
//        }
//
//
//        //遍历所有结果，输出到控制台，上面例子中的"author"、"name"、"readme"都是一个key，其结果则是对应的value
////        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
////            entry.getKey();
////            entry.getValue();
////        }
//    }
//}
