package com.example.oopfinal;

import java.time.LocalDateTime;

/**
 * The type Fault.
 */
public class Fault {
    private int faultID;
    private int itemID;
    private String description;
    private String severity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String itemName;

    /**
     * Instantiates a new Fault.
     *
     * @param faultID     the fault id
     * @param itemID      the item id
     * @param description the description
     * @param severity    the severity
     * @param createdAt   the created at
     * @param updatedAt   the updated at
     */
    public Fault(int faultID, int itemID, String description, String severity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.faultID = faultID;
        this.itemID = itemID;
        this.description = description;
        this.severity = severity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Gets fault id.
     *
     * @return the fault id
     */
    public int getFaultID() {
        return faultID;
    }

    /**
     * Sets fault id.
     *
     * @param faultID the fault id
     */
    public void setFaultID(int faultID) {
        this.faultID = faultID;
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
     * Sets item id.
     *
     * @param itemID the item id
     */
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets severity.
     *
     * @return the severity
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * Sets severity.
     *
     * @param severity the severity
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets updated at.
     *
     * @return the updated at
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets updated at.
     *
     * @param updatedAt the updated at
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}

