package com.m2l.meta.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "ROOM_SCRAP_BOOK")
@Table(name = "room_scrap_book")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RoomScrapBookEntity implements Serializable {

	@Id
	@Column(name = "SCRAP_BOOK_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
	private MyRoomEntity room;

	@OneToMany(mappedBy = "scrapBook")
	private List<Video> videoList;

	@Column(name = "PRICE")
	private BigInteger price;

	@ManyToOne
	@JoinColumn(name = "CHARACTER_ID",referencedColumnName = "CHARACTER_ID")
	private UserCharacter userCharacter;

}
