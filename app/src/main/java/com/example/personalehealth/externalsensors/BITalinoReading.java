/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.personalehealth.externalsensors;


import com.bitalino.comm.BITalinoFrame;

public class BITalinoReading {

  private long timestamp;
  private BITalinoFrame[] frames;

  public BITalinoReading() {
  }

  public BITalinoReading(final long timestamp, final BITalinoFrame[] frames) {
    this.timestamp = timestamp;
    this.frames = frames;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public BITalinoFrame[] getFrames() {
    return frames;
  }

  public void setFrames(BITalinoFrame[] frames) {
    this.frames = frames;
  }

}