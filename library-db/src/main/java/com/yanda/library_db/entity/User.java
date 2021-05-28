package com.yanda.library_db.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * 作者：caibin
 * 时间：2021/5/27 20:22
 * 类说明：
 */
@Entity
public class User {
    @Id
    long id;
    String name;
    int age;
}
