/**
 * filename：TagController.java
 *
 * date：2017年3月22日
 * Copyright reey Corporation 2017
 *
 */
package lol.mifan.myblog.controller;

import lol.mifan.myblog.po.Article;
import lol.mifan.myblog.po.Tag;
import lol.mifan.myblog.service.ArticleService;
import lol.mifan.myblog.service.TagService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/tags", produces="application/json;charset=UTF-8")
public class TagController {

	@Resource
    private TagService tagService;

    @Resource
    private ArticleService articleService;

//    @RequiresPermissions("tag:read")
    @GetMapping(value = "/{id}")
    public Object get(@PathVariable Integer id) {
        Tag tag = tagService.get(id);
        return tagService.toJsonObj(tag);
    }

	@GetMapping
    public List<Object> getList(Integer page, Integer size, String query)  {
        List<Tag> tags = tagService.getList(page, size, query);
        List<Object> list = new ArrayList<>();
        for(Tag tag : tags) {
            list.add(tagService.toJsonObj(tag));
        }
        return list;
    }

	@GetMapping(value = "/{tagId}/articles")
    public Object getArticles(@PathVariable("tagId")int tagId, Integer page, Integer size)  {
        List<Article> articles = articleService.getArticlesByTagId(tagId, page, size);
        return articleService.toJsonArray(articles);
	}


}
