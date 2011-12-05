package com.anders.ssh.webservice.cxf.rs;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/info")
@Produces( { MediaType.APPLICATION_XML })
public interface InfoService
{
	@GET
	// @Path("/info")
	public List<Info> getInfos();

	@GET
	// @Path("/info/{id}")
	@Path("/{id}")
	public Info getInfo(@PathParam("id")
	String id);

	@POST
	// @Path("/info")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
	public void saveOrUpdateInfo(Info info);

	@DELETE
	// @Path("/info/{id}")
	@Path("/{id}")
	public void deleteInfo(@PathParam("id")
	String id);
}
