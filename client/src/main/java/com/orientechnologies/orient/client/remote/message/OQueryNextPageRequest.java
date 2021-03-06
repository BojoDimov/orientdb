/*
 *
 *  *  Copyright 2010-2016 OrientDB LTD (http://orientdb.com)
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *  *
 *  * For more information: http://orientdb.com
 *
 */
package com.orientechnologies.orient.client.remote.message;

import com.orientechnologies.orient.client.binary.OBinaryRequestExecutor;
import com.orientechnologies.orient.client.remote.OBinaryRequest;
import com.orientechnologies.orient.client.remote.OBinaryResponse;
import com.orientechnologies.orient.client.remote.OStorageRemoteSession;
import com.orientechnologies.orient.core.serialization.serializer.record.ORecordSerializer;
import com.orientechnologies.orient.enterprise.channel.binary.OChannelBinaryProtocol;
import com.orientechnologies.orient.enterprise.channel.binary.OChannelDataInput;
import com.orientechnologies.orient.enterprise.channel.binary.OChannelDataOutput;

import java.io.IOException;

public final class OQueryNextPageRequest implements OBinaryRequest<OQueryResponse> {

  private String queryId;
  private int    recordsPerPage;

  public OQueryNextPageRequest(String queryId, int recordsPerPage) {
    this.queryId = queryId;
    this.recordsPerPage = recordsPerPage;
  }

  public OQueryNextPageRequest() {
  }

  @Override
  public void write(OChannelDataOutput network, OStorageRemoteSession session) throws IOException {
    network.writeString(queryId);
    network.writeInt(recordsPerPage);
  }

  public void read(OChannelDataInput channel, int protocolVersion, ORecordSerializer serializer) throws IOException {
    this.queryId = channel.readString();
    this.recordsPerPage = channel.readInt();
  }

  @Override
  public byte getCommand() {
    return OChannelBinaryProtocol.REQUEST_QUERY_NEXT_PAGE;
  }

  @Override
  public String getDescription() {
    return "Execute remote query";
  }

  @Override
  public OQueryResponse createResponse() {
    return new OQueryResponse();
  }

  @Override
  public OBinaryResponse execute(OBinaryRequestExecutor executor) {
    return executor.executeQueryNextPage(this);
  }

  public String getQueryId() {
    return queryId;
  }

  public void setQueryId(String queryId) {
    this.queryId = queryId;
  }

  public int getRecordsPerPage() {
    return recordsPerPage;
  }
}