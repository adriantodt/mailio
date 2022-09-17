//package net.codejava.mail
//
//import java.util.*
//import javax.mail.*
//import javax.mail.Message.RecipientType
//
///**
// * This program demonstrates how to get e-mail messages from a POP3/IMAP server
// *
// * @author www.codejava.net
// */
//class EmailReceiver {
//    /**
//     * Returns a Properties object which is configured for a POP3/IMAP server
//     *
//     * @param protocol either "imap" or "pop3"
//     * @param host
//     * @param port
//     * @return a Properties object
//     */
//    private fun getServerProperties(
//        protocol: String, host: String,
//        port: String
//    ): Properties {
//        val properties = Properties()
//
//        // server setting
//        properties[String.format("mail.%s.host", protocol)] = host
//        properties[String.format("mail.%s.port", protocol)] = port
//
//        // SSL setting
//        properties.setProperty(
//            String.format("mail.%s.socketFactory.class", protocol),
//            "javax.net.ssl.SSLSocketFactory"
//        )
//        properties.setProperty(
//            String.format("mail.%s.socketFactory.fallback", protocol),
//            "false"
//        )
//        properties.setProperty(String.format("mail.%s.socketFactory.port", protocol), port)
//        return properties
//    }
//
//    /**
//     * Downloads new messages and fetches details for each message.
//     * @param protocol
//     * @param host
//     * @param port
//     * @param userName
//     * @param password
//     */
//    fun downloadEmails(
//        protocol: String, host: String, port: String,
//        userName: String?, password: String?
//    ) {
//        val properties = getServerProperties(protocol, host, port)
//        val session: Session = Session.getDefaultInstance(properties)
//        try {
//            // connects to the message store
//            val store: Store = session.getStore(protocol)
//            store.connect(userName, password)
//
//            // opens the inbox folder
//            val folderInbox: Folder = store.getFolder("INBOX")
//            folderInbox.open(Folder.READ_ONLY)
//
//            // fetches new messages from server
//            val messages: Array<Message> = folderInbox.getMessages()
//            for (msg in messages) {
//                val fromAddress: Array<Address> = msg.getFrom()
//                val from: String = fromAddress[0].toString()
//                val subject: String = msg.getSubject()
//                val toList = parseAddresses(
//                    msg
//                        .getRecipients(RecipientType.TO)
//                )
//                val ccList = parseAddresses(
//                    msg
//                        .getRecipients(RecipientType.CC)
//                )
//                val sentDate: String = msg.getSentDate().toString()
//                val contentType: String = msg.getContentType()
//                var messageContent = ""
//                if (contentType.contains("text/plain")
//                    || contentType.contains("text/html")
//                ) {
//                    try {
//                        val content: Any = msg.getContent()
//                        if (content != null) {
//                            messageContent = content.toString()
//                        }
//                    } catch (ex: Exception) {
//                        messageContent = "[Error downloading content]"
//                        ex.printStackTrace()
//                    }
//                }
//
//                // print out details of each message
//                println("Message #" + (i + 1) + ":")
//                println("\t From: $from")
//                println("\t To: $toList")
//                println("\t CC: $ccList")
//                println("\t Subject: $subject")
//                println("\t Sent Date: $sentDate")
//                println("\t Message: $messageContent")
//            }
//
//            // disconnect
//            folderInbox.close(false)
//            store.close()
//        } catch (ex: NoSuchProviderException) {
//            println("No provider for protocol: $protocol")
//            ex.printStackTrace()
//        } catch (ex: MessagingException) {
//            println("Could not connect to the message store")
//            ex.printStackTrace()
//        }
//    }
//
//    /**
//     * Returns a list of addresses in String format separated by comma
//     *
//     * @param address an array of Address objects
//     * @return a string represents a list of addresses
//     */
//    private fun parseAddresses(address: Array<Address>?): String {
//        var listAddress = ""
//        if (address != null) {
//            for (i in address.indices) {
//                listAddress += address[i].toString().toString() + ", "
//            }
//        }
//        if (listAddress.length > 1) {
//            listAddress = listAddress.substring(0, listAddress.length - 2)
//        }
//        return listAddress
//    }
//
//    companion object {
//        /**
//         * Test downloading e-mail messages
//         */
//        @JvmStatic
//        fun main(args: Array<String>) {
//            // for POP3
//            //String protocol = "pop3";
//            //String host = "pop.gmail.com";
//            //String port = "995";
//
//            // for IMAP
//            val protocol = "imap"
//            val host = "imap.gmail.com"
//            val port = "993"
//            val userName = "your_email_address"
//            val password = "your_email_password"
//            val receiver = EmailReceiver()
//            receiver.downloadEmails(protocol, host, port, userName, password)
//        }
//    }
//}