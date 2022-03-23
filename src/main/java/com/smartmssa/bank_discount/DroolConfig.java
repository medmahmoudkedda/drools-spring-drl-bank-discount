package com.smartmssa.bank_discount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.internal.command.CommandFactory;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DroolConfig {

	@Value("${kie.containerId}")
	private String containerId;

	@Value("${kie.server.user}")
	private String user;

	@Value("${kie.server.pwd}")
	private String password;

	@Value("${kie.server.url}")
	private String url;

	private String outIdentifier = "response";

	public RuleServicesClient ruleConfig() {
		KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(url, user, password, 60000);
		config.setMarshallingFormat(MarshallingFormat.JSON);

		RuleServicesClient client = KieServicesFactory.newKieServicesClient(config)
				.getServicesClient(RuleServicesClient.class);

		return client;
	}

	private BatchExecutionCommand batchCommand(Object incomeObj) {
		List<Command<?>> cmds = buildCommands(incomeObj);
		BatchExecutionCommand batchExecutionCommand = CommandFactory.newBatchExecution(cmds);
		return batchExecutionCommand;
	}

	private List<Command<?>> buildCommands(Object incomeObj) {
		List<Command<?>> cmds = new ArrayList<>();
		KieCommands commands = KieServices.Factory.get().getCommands();
		cmds.add(commands.newInsert(incomeObj, outIdentifier));
		cmds.add(commands.newFireAllRules());
		return cmds;
	}

	public Order orderDiscount(Order incomeObj) {
		
		ServiceResponse<ExecutionResults> result = ruleConfig().executeCommandsWithResults(containerId, batchCommand(incomeObj));
		
		if (result.getType() == ServiceResponse.ResponseType.SUCCESS) {
			incomeObj  = (Order) result.getResult().getValue(outIdentifier);
		} else {
			System.out.println("Something went wrong!!");
		}
		return incomeObj;
	}

	public CrediterCompte compteDiscount(CrediterCompte compte) {
		ServiceResponse<ExecutionResults> result = ruleConfig().executeCommandsWithResults(containerId, batchCommand(compte));
		if (result.getType() == ServiceResponse.ResponseType.SUCCESS) {
			compte = (CrediterCompte) result.getResult().getValue(outIdentifier);
		} else {
			System.out.println("Something went wrong!!");
		}
		return compte;
	}

}
