/**
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.common;

import org.drools.FactHandle;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;

public class EventFactHandle extends DefaultFactHandle {

    private static final long serialVersionUID = 5997141759543399455L;

    private long              startTimestamp;
    private long              duration;
    private boolean           expired;
    private long              activationsCount;

    // ----------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------

    public EventFactHandle() {
        super();
        this.startTimestamp = 0;
        this.duration = 0;
    }

    /**
     * Creates a new event fact handle.
     *
     * @param id this event fact handle ID
     * @param object the event object encapsulated in this event fact handle
     * @param recency the recency of this event fact handle
     * @param timestamp the timestamp of the occurrence of this event
     * @param duration the duration of this event. May be 0 (zero) in case this is a primitive event.
     */
    public EventFactHandle(final int id,
                           final Object object,
                           final long recency,
                           final long timestamp,
                           final long duration,
                           final WorkingMemoryEntryPoint wmEntryPoint) {
        super( id,
               object,
               recency,
               wmEntryPoint );
        this.startTimestamp = timestamp;
        this.duration = duration;
    }

    /**
     * @see FactHandle
     * 1: is used for EventFactHandle
     */
    public String toExternalForm() {
        //return "[event fid:" + getId() + ":" + getRecency() + ":" + getObject() + "]";
        return "1:" + this.getId() + ":" + getIdentityHashCode() + ":" + getObjectHashCode() + ":" + getRecency();
    }

    /**
     * @see Object
     */
    public String toString() {
        return toExternalForm();
    }

    /**
     * Always returns true, since the EventFactHandle is
     * only used for Events, and not for regular Facts
     */
    public boolean isEvent() {
        return true;
    }

    /**
     * Returns the timestamp of the occurrence of this event.
     * @return
     */
    public long getStartTimestamp() {
        return startTimestamp;
    }

    /**
     * Returns the duration of this event. In case this is a primitive event,
     * returns 0 (zero).
     *
     * @return
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Returns the end timestamp for this event. This is the same as:
     *
     * startTimestamp + duration
     *
     * @return
     */
    public long getEndTimestamp() {
        return this.startTimestamp + this.duration;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public long getActivationsCount() {
        return activationsCount;
    }

    public void increaseActivationsCount() {
        this.activationsCount++;
    }

    public void decreaseActivationsCount() {
        this.activationsCount--;
    }

    public EventFactHandle clone() {
        EventFactHandle clone = new EventFactHandle( getId(),
                                                      getObject(),
                                                      getRecency(),
                                                      startTimestamp,
                                                      duration,
                                                      getEntryPoint() );
        clone.activationsCount = activationsCount;
        clone.expired = expired;
        clone.setEntryPoint( getEntryPoint() );
        clone.setEqualityKey( getEqualityKey() );
        clone.setFirstLeftTuple( getLastLeftTuple() );
        clone.setLastLeftTuple( getLastLeftTuple() );
        clone.setFirstRightTuple( getFirstRightTuple() );
        clone.setLastRightTuple( getLastRightTuple() );
        clone.setObjectHashCode( getObjectHashCode() );
        return clone;
    }
}
