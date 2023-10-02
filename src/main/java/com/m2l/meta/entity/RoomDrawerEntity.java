package com.m2l.meta.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "ROOM_DRAWER")
@Table(name = "room_drawer")
public class RoomDrawerEntity {

	@Id
	@Column(name = "DRAWER_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ROOM_ID")
	@JsonIgnore
	private MyRoomEntity room;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ITEM_ID")
	private MyRoomItem item;
}
