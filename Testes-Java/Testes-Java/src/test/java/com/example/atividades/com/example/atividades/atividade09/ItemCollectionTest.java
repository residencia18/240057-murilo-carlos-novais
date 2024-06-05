package com.example.atividades.atividade09;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class ItemCollectionTest {

    private ItemCollection itemCollection;

    @Before
    public void setUp() {
        itemCollection = new ItemCollection();
    }

    @Test
    public void testAddItem() {
        Item item = new Item("Item1");
        itemCollection.addItem(item);
        
        List<Item> items = itemCollection.getItems();
        assertTrue(items.contains(item));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItem_nullItem() {
        itemCollection.addItem(null);
    }

    @Test
    public void testRemoveItem() {
        Item item = new Item("Item1");
        itemCollection.addItem(item);
        itemCollection.removeItem(item);
        
        List<Item> items = itemCollection.getItems();
        assertFalse(items.contains(item));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveItem_notInCollection() {
        Item item = new Item("Item1");
        itemCollection.removeItem(item);
    }

    @Test
    public void testGetItems_initiallyEmpty() {
        List<Item> items = itemCollection.getItems();
        assertTrue(items.isEmpty());
    }

    @Test
    public void testGetItems_afterAddingItems() {
        Item item1 = new Item("Item1");
        Item item2 = new Item("Item2");
        itemCollection.addItem(item1);
        itemCollection.addItem(item2);

        List<Item> items = itemCollection.getItems();
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
    }
}
