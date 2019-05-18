case class Email(
                  subject: String,
                  text: String,
                  sender: String,
                  recipient: String
                )

type EmailFilter = Email => Boolean
def newMailsForUser(mails: Seq[Email], f: EmailFilter) = mails.filter(f)

def complement[A](predicate: A => Boolean) = (a: A) => !predicate(a)

val sentByOneOf: Set[String] => EmailFilter =
  senders => email => senders.contains(email.text)
// f.compose(g) is the other way around
val notSentByAnyOf = sentByOneOf andThen (g => complement(g))
val notSentByAnyOf2 = sentByOneOf andThen (complement(_))

type SizeChecker = Int => Boolean
val sizeConstraint: SizeChecker => EmailFilter = f => email => f(email.text.size)

val minimumSize: Int => EmailFilter = n => sizeConstraint(_ >= n)
val maximumSize: Int => EmailFilter = n => sizeConstraint(_ <= n)

def any[A](predicates: (A => Boolean)*): A => Boolean =
  a => predicates.exists(pred => pred(a))
def none[A](predicates: (A => Boolean)*) = complement(any(predicates: _*))
def every[A](predicates: (A => Boolean)*) = none(predicates.view.map(complement(_)): _*)

val filter: EmailFilter = every(
  notSentByAnyOf(Set("johndoe@example.com")),
  minimumSize(100),
  maximumSize(10000)
)

val addMissingSubject = (email: Email) =>
  if (email.subject.isEmpty) email.copy(subject = "No subject")
  else email
val checkSpelling = (email: Email) =>
  email.copy(text = email.text.replaceAll("your", "you're"))
val removeInappropriateLanguage = (email: Email) =>
  email.copy(text = email.text.replaceAll("dynamic typing", "**CENSORED**"))
val addAdvertismentToFooter = (email: Email) =>
  email.copy(text = email.text + "\nThis mail sent via Super Awesome Free Mail")

val pipeline = Function.chain(Seq(
  addMissingSubject,
  checkSpelling,
  removeInappropriateLanguage,
  addAdvertismentToFooter))

val emailFilter: EmailFilter = notSentByAnyOf(Set("johndoe@example.com"))
val mails = Email(
  subject = "It's me again, your stalker friend!",
  text = "Hello my friend! How are you?",
  sender = "johndoe@example.com",
  recipient = "me@example.com") :: Nil
newMailsForUser(mails, emailFilter)

//val handler = fooHandler orElse barHandler orElse bazHandler

