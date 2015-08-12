package com.viorsan.lifetimeline.models;

import java.util.Date;

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 12.08.15.
 */
public class BaseTimelineItem {
    public String description;
    public Date creationDate;
    public BaseTimelineItem(String description, Date creationDate) {
        this.description=description;
        this.creationDate=creationDate;
    }
}
