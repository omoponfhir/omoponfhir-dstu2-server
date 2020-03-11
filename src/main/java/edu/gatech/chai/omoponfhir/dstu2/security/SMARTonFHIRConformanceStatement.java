/*******************************************************************************
 * Copyright (c) 2019 Georgia Tech Research Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
/**
 * 
 */
package edu.gatech.chai.omoponfhir.dstu2.security;

import javax.servlet.http.HttpServletRequest;

import ca.uhn.fhir.model.api.ExtensionDt;
import ca.uhn.fhir.model.dstu2.resource.Conformance;
import ca.uhn.fhir.model.dstu2.resource.Conformance.RestSecurity;
import ca.uhn.fhir.model.dstu2.valueset.RestfulSecurityServiceEnum;
import ca.uhn.fhir.model.primitive.UriDt;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.provider.dstu2.ServerConformanceProvider;

/**
 * @author mc142local
 *
 */
public class SMARTonFHIRConformanceStatement extends ServerConformanceProvider {

	// static String authorizeURI =
	// "http://fhir-registry.smarthealthit.org/Profile/oauth-uris#authorize";
	// static String tokenURI =
	// "http://fhir-registry.smarthealthit.org/Profile/oauth-uris#token";
	// static String registerURI =
	// "http://fhir-registry.smarthealthit.org/Profile/oauth-uris#register";

	static String oauthURI = "http://DSTU2/fhir-registry.smarthealthit.org/StructureDefinition/oauth-uris";
	static String authorizeURI = "authorize";
	static String tokenURI = "token";
	static String registerURI = "register";

	String authorizeURIvalue = "http://localhost:8080/authorize";
	String tokenURIvalue = "http://localhost:8080/token";

	public SMARTonFHIRConformanceStatement() {
		super();
		super.setCache(false);

//		try {
//			InetAddress addr = java.net.InetAddress.getLocalHost();
//			System.out.println(addr);
//			String hostname = addr.getCanonicalHostName();
//			System.out.println("Hostname of system = " + hostname);
//
//			// authorizeURIvalue = "http://"+hostname+":9085/authorize";
//			// tokenURIvalue = "http://"+hostname+":9085/token";
//			// registerURIvalue = "http://"+hostname+":9085/register";
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public SMARTonFHIRConformanceStatement(RestfulServer theRestfulServer) {
		super(theRestfulServer);
		super.setCache(false);

//		try {
//			InetAddress addr = java.net.InetAddress.getLocalHost();
//			System.out.println(addr);
//			String hostname = addr.getCanonicalHostName();
//			System.out.println("Hostname of system = " + hostname);
//
//			// authorizeURIvalue = "http://"+hostname+":9085/authorize";
//			// tokenURIvalue = "http://"+hostname+":9085/token";
//			// registerURIvalue = "http://"+hostname+":9085/register";
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public Conformance getServerConformance(HttpServletRequest theRequest, RequestDetails theRequestDetails) {
		Conformance conformanceStatement = super.getServerConformance(theRequest, theRequestDetails);
		
		RestSecurity restSec = conformanceStatement.getRestFirstRep().getSecurity();
		restSec.addService(RestfulSecurityServiceEnum.SMART_ON_FHIR);
		
		ExtensionDt extension = new ExtensionDt();
		extension.setUrl(oauthURI);
		
		ExtensionDt authorizeExtension = new ExtensionDt();
		authorizeExtension.setUrl(authorizeURI);
		authorizeExtension.setValue(new UriDt(authorizeURIvalue));

		ExtensionDt tokenExtension = new ExtensionDt();
		tokenExtension.setUrl(tokenURI);
		tokenExtension.setValue(new UriDt(tokenURIvalue));
		
		extension.addUndeclaredExtension(authorizeExtension);
		extension.addUndeclaredExtension(tokenExtension);
		restSec.addUndeclaredExtension(extension);
		
		return conformanceStatement;
	}

	public void setAuthServerUrl(String url) {
		authorizeURIvalue = url;
	}
	
	public void setTokenServerUrl(String url) {
		tokenURIvalue = url;
	}
}
