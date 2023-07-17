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
  <title>Liste de vos comptes.</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="<c:url value="css/banque.css"/>" rel="stylesheet" type="text/css">
</head>

<body class="elBody">

<form id="frmListeCompte" name="frmListeCompte" action="<c:url value="ServletHistorique"/>" method="post">

  <table border="0" width="100%">
    <tr>
      <td align="center" valign="top">
        <img src="<c:url value="images/titre.jpg"/>" border="0" height="98" alt=""/>
      </td>
    </tr>
    <tr>
      <td><hr></td>
    </tr>
    <tr>
      <td align="center" >
		<c:if test="${!empty listeCompte}">
			<p class="elTitre2" >Liste de vos comptes sur Net Banque</p>
			<table border="1" width="60%">
				<tr bgcolor="white">
					<td class="elLibelleTableau">Num&eacute;ro</td>
					<td class="elLibelleTableau">D&eacute;signation</td>
					<td class="elLibelleTableau">Taux</td>
					<td class="elLibelleTableau">D&eacute;couvert autoris&eacute;</td>
					<td class="elLibelleTableau">Solde</td>
				</tr>
				<c:forEach items="${listeCompte}"  var="compte" varStatus="iter">
					<tr class="elLigneTableau<c:out value="${iter.count%2+1}"/>">
						<td class="elLibelleTableau">
							<c:url value="historique.smvc" var="urlPourCpt"><c:param name="inNumeroCompte" value="${compte.id}"></c:param></c:url>
							<a href="<c:out value="${urlPourCpt}"/>"><c:out value="${compte.id}"/></a>
						</td>
						
						<td class="elLibelleTableau"><c:out value="${compte.libelle}"/></td>
						
						<td class="elLibelleTableau">
						<c:if test="${! empty compte.taux}"><c:out value="${compte.taux}"/></c:if>
						<c:if test="${empty compte.taux}">--</c:if>
						</td>
						
						<td class="elLibelleTableau">
						<c:if test="${! empty compte.decouvert}"><c:out value="${compte.decouvert}"/></c:if>
						<c:if test="${empty compte.decouvert}">--</c:if>
						</td>

						<td class="elLibelleTableau"><c:out value="${compte.solde}"/></td>  
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${empty listeCompte}">
			Pas de compte pour cet utilisateur.
		</c:if>

        <p>
          <a href="<c:url value="menu.smvc"/>">
            <img src="<c:url value="images/menu.gif"/>" width="98" height="33" border="0" alt="" />
          </a>
        </p>
      </td>
    </tr>
  </table>
</form>

</body>
</html>
