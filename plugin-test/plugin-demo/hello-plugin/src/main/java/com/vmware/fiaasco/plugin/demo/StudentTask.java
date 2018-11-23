/*
 * Copyright (c) 2018 VMware, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, without warranties or
 * conditions of any kind, EITHER EXPRESS OR IMPLIED.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.vmware.fiaasco.plugin.demo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pf4j.Extension;

import com.vmware.fiaasco.plugin.api.TaskExtensionPoint;

/**
 * Insert your comment for StudentTask here
 *
 * @author kumargautam
 */
@Extension
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentTask extends TaskExtensionPoint<Student> {

    private String taskName;
}
