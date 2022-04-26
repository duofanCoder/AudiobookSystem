package com.abao.as.model.business;

import com.abao.as.model.common.File;
import com.abao.as.model.common.User;
import com.abao.as.model.enums.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "biz_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createTime;
    private Date updateTime;
    private String name;
    private String description;
    private String author;
    private String img;
    @Enumerated(EnumType.STRING)
    private Category category;
    @OneToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File file;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Chapter> chapterList;

    @ManyToMany(mappedBy = "bookList")
    private List<User> userList = new ArrayList<>();

}
