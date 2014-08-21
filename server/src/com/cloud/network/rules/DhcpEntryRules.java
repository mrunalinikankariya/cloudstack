// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package com.cloud.network.rules;

import org.apache.cloudstack.network.topology.NetworkTopologyVisitor;

import com.cloud.deploy.DeployDestination;
import com.cloud.exception.ResourceUnavailableException;
import com.cloud.network.Network;
import com.cloud.network.router.VirtualRouter;
import com.cloud.vm.NicProfile;
import com.cloud.vm.NicVO;
import com.cloud.vm.UserVmVO;
import com.cloud.vm.VirtualMachineProfile;

public class DhcpEntryRules extends RuleApplier {

    private final NicProfile _nic;
    private final VirtualMachineProfile _profile;
    private final DeployDestination _destination;

    private NicVO _nicVo;
    private UserVmVO _userVM;

    public DhcpEntryRules(final Network network, final NicProfile nic, final VirtualMachineProfile profile, final DeployDestination destination) {
        super(network);

        _nic = nic;
        _profile = profile;
        _destination = destination;
    }

    @Override
    public boolean accept(final NetworkTopologyVisitor visitor, final VirtualRouter router) throws ResourceUnavailableException {
        _router = router;

        _userVM = _userVmDao.findById(_profile.getId());
        _userVmDao.loadDetails(_userVM);
        _nicVo = _nicDao.findById(_nic.getId());

        return visitor.visit(this);
    }

    public NicProfile getNic() {
        return _nic;
    }

    public NicVO getNicVo() {
        return _nicVo;
    }

    public UserVmVO getUserVM() {
        return _userVM;
    }
}