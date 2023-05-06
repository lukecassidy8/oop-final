package com.example.oopfinal;

import java.util.Date;

/**
 * The type Item.
 */
public class Item {
    private int itemID;
    private String itemName;
    private String itemDesc;
    private Date loanDate;
    private Date returnDate;
    private int loanedOut;
    private long daysUntilReturn;

    /**
     * Instantiates a new Item.
     *
     * @param itemName   the item name
     * @param itemID     the item id
     * @param loanDate   the loan date
     * @param returnDate the return date
     * @param itemDesc   the item desc
     * @param loanedOut  the loaned out
     */
    public Item(String itemName, int itemID, java.sql.Date loanDate, java.sql.Date returnDate, String itemDesc, int loanedOut) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.loanedOut = loanedOut;
    }

    /**
     * Sets days until return.
     *
     * @param daysUntilReturn the days until return
     */
    public void setDaysUntilReturn(long daysUntilReturn) {
        this.daysUntilReturn = daysUntilReturn;
    }

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public int getItemID() {
        return itemID;
    }

    /**
     * Gets item name.
     *
     * @return the item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets item desc.
     *
     * @return the item desc
     */
    public String getItemDesc() {
        return itemDesc;
    }

    /**
     * Gets loan date.
     *
     * @return the loan date
     */
    public Date getLoanDate() {
        return loanDate;
    }

    /**
     * Gets return date.
     *
     * @return the return date
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * Gets loaned out.
     *
     * @return the loaned out
     */
    public int getLoanedOut() {
        return loanedOut;
    }

    /**
     * Gets days until return.
     *
     * @return the days until return
     */
    public long getDaysUntilReturn() {
        return daysUntilReturn;
    }
}

