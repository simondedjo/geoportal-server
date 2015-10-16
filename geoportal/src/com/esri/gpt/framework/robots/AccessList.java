/* See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * Esri Inc. licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.esri.gpt.framework.robots;

import com.esri.gpt.framework.util.StringBuilderWriter;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Access list.
 */
class AccessList {
  private final List<AccessImpl> accessList = new ArrayList<AccessImpl>();
  
  /**
   * Adds access to the list.
   * @param access access
   */
  public void addAccess(AccessImpl access) {
    accessList.add(access);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    PrintWriter writer = new PrintWriter(new StringBuilderWriter(sb));
    
    for (AccessImpl access: accessList) {
      writer.println(access.toString());
    }
    
    // no need to close writer or catch any exception
    
    return sb.toString();
  }
  
  /**
   * Checks if relative path has access.
   * @param relativePath relative path
   * @return <code>true</code> if path has access
   */
  public AccessImpl findAccess(String relativePath) {
    List<AccessImpl> allMatching = selectMatching(relativePath);
    int maxLength = findMaxLength(allMatching);
    AccessImpl firstMatching = findFirstByLength(allMatching, maxLength);
    
    return firstMatching;
  }
  
  private AccessImpl findFirstByLength(List<AccessImpl> allMatching, int length) {
    for (AccessImpl acc: allMatching) {
      if (acc.getLenth()==length) {
        return acc;
      }
    }
    return null;
  }
  
  private int findMaxLength(List<AccessImpl> allMatching) {
    int length = 0;
    for (AccessImpl acc: allMatching) {
      if (acc.getLenth()>length) {
        length = acc.getLenth();
      }
    }
    return length;
  }
  
  private List<AccessImpl> selectMatching(String relativePath) {
    List<AccessImpl> allMatching = new ArrayList<AccessImpl>();
    for (AccessImpl acc: accessList) {
      if (acc.matches(relativePath)) {
        allMatching.add(acc);
      }
    }
    return allMatching;
  }
}
