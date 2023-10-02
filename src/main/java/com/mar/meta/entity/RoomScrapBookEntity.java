package com.mar.meta.entity;

import jakarta.persistence.*;
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
@Entity(name = "RoomScrapBook")
@Table(name = "ROOM_SCRAP_BOOK")
public class RoomScrapBookEntity {

	@Id
	@Column(name = "SCRAP_BOOK_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
	private MyRoomEntity room;
}
