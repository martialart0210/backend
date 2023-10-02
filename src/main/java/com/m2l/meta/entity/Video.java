package com.m2l.meta.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * The Class Video.
 *
 * @author <a href="mailto:an@dxplus.io">an</a>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "VIDEO")
@Table(name = "videos")
public class Video {
    private static final long serialVersionUID = 7629287212050058050L;
    @Id
    @Column(name = "VIDEO_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATED_DATE")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @Column(name = "VIDEO_LINK", length = 1000)
    private String videoLink;

    @Column(name = "PLAYLIST")
    private String playlist;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "SCRAP_BOOK_ID", referencedColumnName = "SCRAP_BOOK_ID")
    private RoomScrapBookEntity scrapBook;

}
