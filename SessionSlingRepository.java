package com.adobe.aem.guides.demo.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = {
        Servlet.class
}, property = {
        "sling.servlet.methods=get",
        "sling.servlet.paths=/bin/config/SessionSlingRepository"
})
public class SessionSlingRepository extends SlingAllMethodsServlet
{
    @Reference
    SlingRepository slingRepository;
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            Session session=slingRepository.login(new SimpleCredentials("admin", "admin".toCharArray()));
            Node node =session.getRootNode();
            Node subNode=node.addNode("deekshi-node");
            subNode.setProperty("description", "thid is my first trial");
            subNode.setPrimaryType("cq:Component");

            session.save();
            response.getWriter().write(String.valueOf(subNode.getProperty("description")));
            response.getWriter().write("The node created successfuly using the session");



        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }


    }
}
