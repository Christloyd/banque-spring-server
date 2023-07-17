<%@ page language="java"
	contentType="text/html; charset=UTF-8"
  	pageEncoding="UTF-8"
  	buffer="128kb"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Gestion des Virements</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="<c:url value="css/banque.css"/>" rel="stylesheet" type="text/css">
</head>

<body  class="elBody">

<form:form action="dovirement.smvc" method="post" modelAttribute="virementBean" >

  <table border="0" width="100%" align="center">
    <tr>
      <td align="center" valign="top">
        <img src="<c:url value="images/titre.jpg"/>" border="0" height="98" alt="" />
      </td>
    </tr>
    <tr>
      <td>
        <hr>
      </td>
    </tr>
    <tr>
      <td>
      	 <spring:hasBindErrors name="virementBean">
          	<ul>
          		<c:forEach items="${errors.allErrors}" var="erreur">
   					<%-- Fonctionne avec le bean ResourceBundleMessageSource --%>
					<li><spring:message code="${erreur.defaultMessage}" /></li>
          		</c:forEach>
          	</ul>
          </spring:hasBindErrors>
		<c:if test="${!empty message}">
		<p><spring:message code="${message}" /></p>
		</c:if>
      </td>
    </tr>

	<c:if test="${empty message}">
    <tr>
      <td>
		 <br/><br/>
         <table width="60%" border="1" align="center">
           <tr>
             <td align="left">
               &nbsp;<img src="<c:url value="images/puce.gif"/>" width="13" height="18" alt=""/>&nbsp;Comptes &eacute;metteurs
             </td>
             <td>
             	<form:select path="cptSrcId" >
				   <form:option value="" label="--- Choix ---"/>
				   <form:options items="${listeCompte}" itemLabel="libelle" itemValue="id" />             	
             	</form:select>
             	
             </td>
           </tr>
           <tr>
             <td align="left">
               &nbsp;<img src="<c:url value="images/puce.gif"/>" width="13" height="18" alt=""/>&nbsp;Comptes destinataires
            </td>
             <td>
				<form:select path="cptDstId" >
				   <form:option value="" label="--- Choix ---"/>
				   <form:options items="${listeCompte}" itemLabel="libelle" itemValue="id" />             	
				</form:select>               
             </td>
           </tr>
           <tr>
             <td align="left">
               &nbsp;<img src="<c:url value="images/puce.gif"/>" width="13" height="18" alt=""/>&nbsp;Montant du virement
             </td>
             <td>
              <form:input path="montant" size="12"/>
             </td>
           </tr>
         </table>
         <p>&nbsp;</p>
         <table width="200" border="0" align="center">
           <tr>
             <td><a href="javascript:virementBean.submit()" >
                <img src="<c:url value="images/bouton-validez.gif"/>" width="98" height="33" border="0" alt=""/>
                </a>
             </td>
           </tr>
         </table>
       </td>
    </tr>
    </c:if>
    <tr>
    	<td><a href="<c:url value="menu.smvc"/>" >
            <img src="<c:url value="images/menu.gif"/>" width="98" height="34" border="0" alt=""/>
            </a>
        </td>
    </tr>
  </table>
</form:form>
</body>
</html>
