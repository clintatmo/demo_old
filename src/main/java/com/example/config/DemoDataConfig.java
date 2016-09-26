package com.example.config;

import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;


@Configuration
public class DemoDataConfig {

  protected static final Logger LOGGER = LoggerFactory.getLogger(DemoDataConfig.class);
  public static final String SUPERVISOR = "supervisor";
  public static final String RETAIL_OFFICER = "retail-officer";
  public static final String RETAIL_MANAGER = "retail-manager";
  public static final String BRANCH_MANAGER = "branch-manager";
  public static final String LEGAL = "legal";
  public static final String GOEDKEURINGS_STRUKTUUR = "goedkeurings-structuur";
  public static final String USER = "user";


  @Autowired
  protected IdentityService identityService;
  
  @Autowired
  protected RepositoryService repositoryService;
  
  @Autowired
  protected RuntimeService runtimeService;
  
  @Autowired
  protected TaskService taskService;
  
  @Autowired
  protected ManagementService managementService;
  
  @Autowired
  protected ProcessEngineConfigurationImpl processEngineConfiguration;

  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Autowired
  protected Environment environment;
  
  @PostConstruct
  public void init() {
    if (Boolean.valueOf(environment.getProperty("create.demo.users", "true"))) {
      LOGGER.info("Initializing demo groups");
      initDemoGroups();
      LOGGER.info("Initializing demo users");
      initDemoUsers();
    }
    if (Boolean.valueOf(environment.getProperty("create.demo.definitions", "true"))) {
      LOGGER.info("Initializing demo process definitions");
      initProcessDefinitions();
    }

  }
  
  protected void initDemoGroups() {
    String[] assignmentGroups = new String[] {SUPERVISOR, RETAIL_OFFICER, RETAIL_MANAGER, BRANCH_MANAGER, LEGAL, GOEDKEURINGS_STRUKTUUR};
    for (String groupId : assignmentGroups) {
      createGroup(groupId, "assignment");
    }
    
    String[] securityGroups = new String[] {USER, "admin"};
    for (String groupId : securityGroups) {
      createGroup(groupId, "security-role");
    }
  }
  
  protected void createGroup(String groupId, String type) {
    if (identityService.createGroupQuery().groupId(groupId).count() == 0) {
      Group newGroup = identityService.newGroup(groupId);
      newGroup.setName(groupId.substring(0, 1).toUpperCase() + groupId.substring(1));
      newGroup.setType(type);
      identityService.saveGroup(newGroup);
    }
  }

  protected void initDemoUsers() {
    createUser("kermit", "Kermit", "The Frog", "kermit", "kermit@activiti.org",
            "org/activiti/explorer/images/kermit.jpg",
            Arrays.asList(SUPERVISOR, USER),
            Arrays.asList("birthDate", "10-10-1955", "jobTitle", "Muppet", "location", "Hollywoord",
                          "phone", "+123456789", "twitterName", "alfresco", "skype", "activiti_kermit_frog"));
    
    createUser("gonzo", "Gonzo", "The Great", "gonzo", "gonzo@activiti.org", 
            "org/activiti/explorer/images/gonzo.jpg",
            Arrays.asList(RETAIL_OFFICER, USER),
            null);
    createUser("fozzie", "Fozzie", "Bear", "fozzie", "fozzie@activiti.org", 
            "org/activiti/explorer/images/fozzie.jpg",
            Arrays.asList(RETAIL_MANAGER, USER),
            null);
    createUser("gs", "Goedkeurings", "Struktuur", "gs", "goekeuring@struktuur.org",
            "org/activiti/explorer/images/fozzie.jpg",
            Arrays.asList(GOEDKEURINGS_STRUKTUUR, USER),
            null);
    createUser("bm", "Branch", "Manager", "bm", "branch@manager.org",
            "org/activiti/explorer/images/fozzie.jpg",
            Arrays.asList(BRANCH_MANAGER, USER),
            null);
    createUser("legal", "Legal", "Officer", "legal", "legal@officer.org",
            "org/activiti/explorer/images/fozzie.jpg",
            Arrays.asList(LEGAL, USER),
            null);
  }
  
  protected void createUser(String userId, String firstName, String lastName, String password, 
          String email, String imageResource, List<String> groups, List<String> userInfo) {
    
    if (identityService.createUserQuery().userId(userId).count() == 0) {
      
      // Following data can already be set by demo setup script
      
      User user = identityService.newUser(userId);
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setPassword(passwordEncoder.encode(password));
      user.setEmail(email);
      identityService.saveUser(user);
      
      if (groups != null) {
        for (String group : groups) {
          identityService.createMembership(userId, group);
        }
      }
    }
    
    // Following data is not set by demo setup script
      
    // image
/*    if (imageResource != null) {
      byte[] pictureBytes = IoUtil.readInputStream(this.getClass().getClassLoader().getResourceAsStream(imageResource), null);
      Picture picture = new Picture(pictureBytes, "image/jpeg");
      identityService.setUserPicture(userId, picture);
    }
      
    // user info
    if (userInfo != null) {
      for(int i=0; i<userInfo.size(); i+=2) {
        identityService.setUserInfo(userId, userInfo.get(i), userInfo.get(i+1));
      }
    }*/
    
  }
  
  protected void initProcessDefinitions() {
    
    String deploymentName = "DEV processes";
    List<Deployment> deploymentList = repositoryService.createDeploymentQuery().deploymentName(deploymentName).list();
    
    //if (deploymentList == null || deploymentList.isEmpty()) {
      repositoryService.createDeployment()
        .name(deploymentName)
        //.addClasspathResource("processes/FinaBankLoanProcess.bpmn20.xml")
        //.addClasspathResource("processes/commerciele_hypotheek_proces.bpmn20.xml")
        .addClasspathResource("proces/generic_loan_proces_flow.bpmn20.xml")
        .deploy();
    //}

  }

}
