package com.mar.meta.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "MYROOM")
@Table(name = "myroom")
public class MyRoomEntity {

	@Id
	@Column(name = "ROOM_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "LEVEL")
	private int level;
	
	@OneToOne(mappedBy = "room")
	private RoomDrawerEntity drawerEntity;
	
	@OneToOne(mappedBy = "room")
	private RoomWardrobeEntity wardrobeEntity;
	
	@OneToOne(mappedBy = "room")
	private RoomScrapBookEntity scrapBookEntity;
	
	
	
}
