package com.m2l.meta.wrap_class;

import com.m2l.meta.dto.DojoDto;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DojoDtoList implements List<DojoDto> {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<DojoDto> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(DojoDto dto) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends DojoDto> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends DojoDto> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public DojoDto get(int index) {
        return null;
    }

    @Override
    public DojoDto set(int index, DojoDto element) {
        return null;
    }

    @Override
    public void add(int index, DojoDto element) {

    }

    @Override
    public DojoDto remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<DojoDto> listIterator() {
        return null;
    }

    @Override
    public ListIterator<DojoDto> listIterator(int index) {
        return null;
    }

    @Override
    public List<DojoDto> subList(int fromIndex, int toIndex) {
        return null;
    }
}
