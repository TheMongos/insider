package utils;

import db.mysql.Item;
import db.redis.ItemRanks;

public class ItemRes {
 private Item itemDetails;
 private ItemRanks itemRanks;
public Item getItemDetails() {
	return itemDetails;
}
public void setItemDetails(Item itemDetails) {
	this.itemDetails = itemDetails;
}
public ItemRanks getItemRanks() {
	return itemRanks;
}
public void setItemRanks(ItemRanks itemRanks) {
	this.itemRanks = itemRanks;
}
 
}
