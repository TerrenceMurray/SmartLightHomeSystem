package com.terrencemurray.app.utility;

/**
 * Interface for objects that can be stored in sets and collections.
 * Ensures proper equals and hashCode implementation for collection storage.
 * 
 * @author Terrence Murray
 * @version 1.0
 */
public interface SetItem {
    /**
     * Compares this object with another for equality.
     * @param o the object to compare with
     * @return true if objects are equal
     */
    public boolean equals(Object o);
    
    /**
     * Returns a hash code for this object.
     * @return the hash code value
     */
    public int hashCode();
}
