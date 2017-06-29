//package lol.mifan.myblog.util.webmagic;
//
//
//import org.jsoup.helper.StringUtil;
//import org.springframework.stereotype.Component;
//import us.codecraft.webmagic.Page;
//import us.codecraft.webmagic.Site;
//import us.codecraft.webmagic.Spider;
//import us.codecraft.webmagic.pipeline.ConsolePipeline;
//import us.codecraft.webmagic.processor.PageProcessor;
//import us.codecraft.webmagic.selector.Selectable;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@Component
//public class ZhihuDailyPageProcesser implements PageProcessor {
//
//    @Resource
//    private DatabasePipeline pipeline;
//
//
//    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
//
//    @Override
//    public void process(Page page) {
////        List<String> links = page.getHtml().links().regex("http://my\\.oschina\\.net/flashsword/blog/\\d+").all();
//
//
//        List<String> links = page.getHtml().$(".box a", "href").all();
//
//        if (links.size() > 0) {
//            page.addTargetRequests(links);
//            page.setSkip(true);
//        } else {
//            page.putField("title", page.getHtml().$(".headline-title", "text").get());
//            page.putField("title_img", page.getHtml().$(".img-wrap img", "src").get());
//
//            List<String> tagList = new ArrayList<>();
//
//            switch (new Random().nextInt(6)) {
//                case 0:
//                    tagList.add("首页");
//                    break;
//                case 1:
//                    tagList.add("电影日报");
//                    break;
//                case 2:
//                    tagList.add("设计日报");
//                    break;
//                case 3:
//                    tagList.add("游戏日报");
//                    break;
//                case 4:
//                    tagList.add("动漫日报");
//                    break;
//                case 5:
//                    tagList.add("互联网日报");
//                    break;
//            }
//
//            page.putField("tags", tagList);
//
//            List<Selectable> questions = page.getHtml().$(".question").nodes();
//            StringBuilder sb = new StringBuilder();
//            String outline = null;
//            for (Selectable question : questions) {
//                String t = question.$(".question-title", "text").get();//问题标题
//                String c = question.$(".content", "innerHTML").get();//问题回答
//
//                //日报格式很奇怪
//                if (StringUtil.isBlank(t)) {//如果标题为空，内容却正常
//                    if (c != null && c.length() > 2000) {
//                        sb.append(c);
//
//                        if (outline == null) {
//                            outline = question.xpath("//div[@class='content']/p[1]//allText()").get();//提取文本
//                        }
//                    }
//                } else if (!StringUtil.isBlank(c)) {//如果标题不为空，内容不为空
//                    sb.append("<h2>").append(t).append("</h2>");
//                    sb.append(c);
//
//                    if (outline == null) {
//                        outline = t + question.xpath("//div[@class='content']/p[1]//allText()").get();//提取文本
//                    }
//                }
//
//
//            }
//            String content = sb.toString();
//            if (StringUtil.isBlank(content)) {//如果爬取内容为空
//                page.setSkip(true);
//            } else {
//                page.putField("content", content);
//                page.putField("outline", outline);
//            }
//        }
//    }
//
//    @Override
//    public Site getSite() {
//        return site;
//
//    }
//
//    public void start() {
//        Spider.create(new ZhihuDailyPageProcesser())
//                .addUrl("http://daily.zhihu.com/")
//                .addPipeline(pipeline).run();
//    }
//
//    public static void main(String[] a) {
//        Spider.create(new ZhihuDailyPageProcesser())
//                .addUrl("http://daily.zhihu.com/")
//                .addPipeline(new ConsolePipeline()).run();
//    }
//}
//
