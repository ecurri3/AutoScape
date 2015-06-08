package com.evan.autoscape.autoscape;

/**
 * Created by EvanOwns on 6/7/2015.
 */
public class ItemData {

    public Item[] allItems;

    public Item[] goblinTable;
    public Item[] skeletonTable;

    public ItemData(){
        allItems = loadItems();

        goblinTable = createGoblinTable();
        skeletonTable = createSkeletonTable();
    }

    public Item[] loadItems(){
        Item table[] = new Item[6];

        table[0] = new Item(5, 1, "Bones", "Bones are for burying!", 1);
        table[1] = new Item(15, 2, "Raw Meat", "I should cook this.", 1);
        table[2] = new Item(100, 3, "Bronze Longsword", "A Bronze Longsword", 1);
        table[3] = new Item(50, 4, "Red Bead", "A magic bead.", 1);
        table[4] = new Item(69696969, 1001, "Blue Partyhat", "Holy Shit WTF", 1);
        table[5] = new Item(11000, 100, "Rune Medium Helmet", "Rune med sux", 1);

        return table;
    }

    public Item[] createGoblinTable(){
        Item table[] = new Item[10];

        table[0] = allItems[0];
        table[1] = allItems[0];
        table[2] = allItems[0];
        table[3] = allItems[0];
        table[4] = allItems[0];
        table[5] = allItems[0];
        table[6] = allItems[0];
        table[7] = allItems[0];
        table[8] = allItems[4];
        table[9] = allItems[5];

        return table;
    }

    public Item[] createSkeletonTable(){
        Item table[] = new Item[10];

        table[0] = allItems[0];
        table[1] = allItems[1];
        table[2] = allItems[0];
        table[3] = allItems[0];
        table[4] = allItems[0];
        table[5] = allItems[0];
        table[6] = allItems[0];
        table[7] = allItems[0];
        table[8] = allItems[3];
        table[9] = allItems[2];

        return table;
    }
}
