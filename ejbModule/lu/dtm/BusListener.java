/**
 *
 */
package lu.dtm;



import javax.jms.ObjectMessage;

import tools.utils.event.TimeClockEvent;



import lu.dtm.appli.ruleengine.StatefulRuleEngine;
import lu.dtm.locator.AppliServiceLocator;
import lu.dtm.locator.ServiceLocator;
import lu.dtm.locator.ServiceLocatorFactory;






/**
 * <!-- begin-xdoclet-definition -->
 * @ejb.bean name="busListener"
 *     acknowledge-mode="Auto-acknowledge"
 *     destination-type="javax.jms.Topic"
 *     subscription-durability="NonDurable"
 *     transaction-type="Bean"
 *     destination-jndi-name="busListener"
 *
 *  @ejb.transaction="Supports"
 *
 * <!-- end-xdoclet-definition -->
 * @generated
 **/

public class BusListener implements javax.ejb.MessageDrivenBean,
		javax.jms.MessageListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private StatefulRuleEngine 	ruleEngine = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * The context for the message-driven bean, set by the EJB container.
	 * @generated
	 */
	private javax.ejb.MessageDrivenContext messageContext = null;

	/**
	 * Required method for container to set context.
	 * @generated
	 */
	public void setMessageDrivenContext(
			javax.ejb.MessageDrivenContext messageContext)
			throws javax.ejb.EJBException {
		this.messageContext = messageContext;
	}

	/**
	 * Required creation method for message-driven beans.
	 *
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * <!-- begin-xdoclet-definition -->
	 * @ejb.create-method
	 * <!-- end-xdoclet-definition -->
	 * @generated
	 */
	public void ejbCreate() {
		try {
			ruleEngine = (StatefulRuleEngine) ServiceLocatorFactory.loadServiceLocator(AppliServiceLocator.SOCIETE_A, ServiceLocator.APPLI_CHECKLIST).getService(AppliServiceLocator.STATEFUL_RULE_ENGINE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Required removal method for message-driven beans.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void ejbRemove() {
		messageContext = null;
	}

	/**
	 * This method implements the business logic for the EJB.
	 *
	 * <p>Make sure that the business logic accounts for asynchronous message processing.
	 * For example, it cannot be assumed that the EJB receives messages in the order they were
	 * sent by the client. Instance pooling within the container means that messages are not
	 * received or processed in a sequential order, although individual onMessage() calls to
	 * a given message-driven bean instance are serialized.
	 *
	 * <p>The <code>onMessage()</code> method is required, and must take a single parameter
	 * of type javax.jms.Message. The throws clause (if used) must not include an application
	 * exception. Must not be declared as final or static.
	 *
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void onMessage(javax.jms.Message message) {
//		System.out.println("Message Driven Bean [BusListener] got message " + message);
		try {
			ObjectMessage objMsg = (ObjectMessage) message;
			message.acknowledge();

//			System.out.println("Message's object class : "+objMsg.getObject().getClass().getName());
//			System.out.println("Message's content : "+objMsg.getObject());

			if(objMsg.getObject() instanceof TimeClockEvent){
//				System.out.println("Réception d'un timeclock");
				ruleEngine.setTime((TimeClockEvent) objMsg.getObject());
			}


			/*
			 * Methode temporaire
			 */

			/*String timeString = objMsg.getObject().toString();
			int startIndex = timeString.indexOf("=")+1;
			timeString = timeString.substring(startIndex);

			String dateFormat = "EEE MMM dd HH:mm:ss zzz yyyy";

			ParsePosition parsePosition = new ParsePosition(0);

			Date date = new SimpleDateFormat(dateFormat, Locale.ENGLISH).parse(timeString,parsePosition);
			TimeClockEvent timeClock = new TimeClockEvent(date);
			ruleEngine.setTime(timeClock);*/


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	public BusListener() {
		// TODO Auto-generated constructor stub
	}
}
