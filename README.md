
# MENTCARE - PROGETTO INGEGNERIA DEL SOFTWARE 2023/2024

Mirko Pavan VR407143

## REQUISITI

Questo progetto si basa sullo sviluppo di un sistema informativo di cliniche che gestiscono pazienti con patologie mentali.
Si è cercato di implementare almeno uno scenario per la maggior parte dei fruitori di questo sistema informativo.
Siccome ogni fruitore deve avere un cono di visibilità limitato al proprio ruolo, è previsto un sistema di login legato 
al ruolo per evitare che si possa accedere a dati riservati.
La Reception svolge la funzione di censire i nuovi pazienti e ricevere le prenotazioni.
Gli slot orari prenotabili sono dal lunedì al venerdì dalle 10.00 alle 17.00 con frequenza di 30 minuti.
gli infermieri hanno la possibilità di visualizzare il paziente ed i medicinali prescritti, mentre i medici, oltre a
visualizzare le informazioni del paziente e dei farmaci, possono anche prescriverli.
Esiste poi la figura del manager che avrà visibilità di reportistica (ma non sarà implementata).
Si prevede che il sistema sia facilmente comprensibile ed utilizzabile dopo una formazione minima di 4 ore e che dopo 
questo addestramento, gli errori non eccedano i 2 per ogni ora di utilizzo del sistema.
Alcuni degli scenari descritti, avrebbero potuto essere spezzati in più scenari più piccoli, tuttavia, avendo un numero limitato di
scenari da implementare ed essendo alcuni scenari potenzialmente troppo piccoli, ho deciso di accorpare più funzionalità correlate in
singoli scenari.


## SCENARI

### 1. __Login__
**Assunzione iniziale:** nell'organizzazione esistono 4 differenti tipi di ruolo: *Segreteria*, *Infermiera*, *Medico*, 
*Manager*. Ogni tipologia di ruolo ha la necessità di poter visualizzare informazioni diverse e l'obbligo di non poterne
visualizzare alcune. E' importante quindi che ogni funzione abbia un accesso distinto.

**Situazione normale:** Il lavoratore col proprio ruolo, utilizzando le credenziali fornite in precedenza, clicca sul
pulsante login e riesce a visualizzare la pagina di benvenuto.
in base al proprio ruolo, vedrà un menù a tendina ad hoc con le proprie funzionalità.

- *Receptionist*: Home, New Patient, Patients List, Appointment List, Logout.
- *Infermiera*: Home, View Record, Edit Record, Logout.
- *Medico*: Home, Patients List, View Record, Edit Record, Setup consultation, Generate report, Logout.
- *Manager*: Home, Patients List, Export statistics, Generate report, Logout.

**Cosa può andare storto:** L'username e password sono errate. Il sistema non ti permetterà di accedere al sistema.

**Stato del sistema al completamento:** il lavoratore riuscirà ad accedere e potrà proseguire a navigare la pagina web.

### 2. __Receptionist - Inserimento nuovo paziente__
**Assunzione iniziale:** il receptionist è quel ruolo si occupa di censire i nuovi pazienti. Essendoci pazienti con 
patologie mentali, è importante venga censito un indirizzo mail (per potenziale notifica in caso di appuntamento) e lo
stato di pericolosità del paziente (per rendere noto a tutto il personale di gestire l'ospite con particolari accortezze)

**Situazione normale:** Il paziente, al desk della segreteria fornisce le proprie informazioni di base, che verranno 
inserita dall'addetto alla reception. L'addetto alla reception, dovrà accedere al sistema MentCare tramite login,
cliccare sul menù a tendina "New Patient" ed inserire le informazioni. Verrà inserito Nome, Cognome, indirizzo mail e 
livello di pericolosità.
Tutti i campi sono **obbligatori**. L'indirizzo mail dev'essere ben formato e il livello di pericolosità verrà scelto 
tramite menù a tendina. Una volta che il paziente verrà cesito, il sistema tornerà all'homepage.
Per poter visualizzare la lista dei pazienti censiti, l'addetto reception dovrà selezionare il menù a tendina "Patients 
List" e vedrà le informazioni appena censite.

**Cosa può andare storto:** nel caso in cui venissero omessi uno o più campi o l'indirizzo email fosse mal formato,
apparirà una scritta di errore e non si potrà proseguire con l'inserimento.

**Altre attività**: l'addetto alla reception potrà inserire un appuntamento.

**Stato del sistema al completamento:** il lavoratore riuscirà ad accedere e potrà proseguire a navigare la pagina web.

### 3. __Receptionist - Gestione appuntamenti paziente__
**Assunzione iniziale:** i pazienti per poter essere visitati hanno bisogno di una prenotazione. I giorni prenotabili 
vanno dal lunedì al venerdì dalle 9:00 alle 17:00 (mezz'ora dopo l'apertura dei centri e mezz'ora prima della chiusura).
Non si può prenotare ad una distanza superiore di un anno e ci può essere al massimo un paziente per slot (30 minuti).

**Situazione normale:** Per censire un nuovo appuntamento, la receptionist accede al sistema tramite credenziali.
A seguito del'accesso, clicca su "Patient List" e cerca il paziente a cui dare il nuovo appuntamento.
Clicca sulla riga corrispettiva al paziente, il tasto "new appointment".
A quel punto apparirà una pagina con un menù a tendina di tutti gli appuntamenti attualmente richiedibili.
Se un altro paziente ha già chiesto uno slot, quello **non sarà più visibile** nel menù a tendina.
Dopo aver deciso assieme al paziente lo slot di giorno ed ora preferiti, vengono selezionati tramite menù a tendina e 
verrà cliccato il tasto "invia". In caso di successo apparirà la scritta "appointment correctly inserted."

Per visualizzare la lista dei prossimi appuntamenti dei pazienti, tramite menù di navigazione, si clicca sul tasto 
"Appointment List"

**Cosa può andare storto:** Il paziente non è ancora stato censito ma viene richiesto l'appuntamento. Sarà necessario
prima censirlo a sistema. Se invece non verrà selezionato lo slot orario, apparirà un messaggio d'errore.

**Stato del sistema al completamento:** un nuovo appuntamento sarà correttamente registrato e lo slot non sarà più 
disponibile nella lista degli appuntamenti.

### 4. __Infermiera - Visualizzazione paziente__

**Assunzione iniziale:** L'infermiera copre quella funzione che dovrà poter visualizzare i dati del paziente e la lista
delle somministrazioni.

**Situazione normale:** l'infermiera ha bisogno di visualizzare la cartella di un paziente.
Accederà al sistema tramite login e col pulsante "View Record" apparirà una barra di ricerca per poter cercare il
singolo paziente. Potrà inserire nome e/o cognome. In caso di più risultati, appariranno più righe nel risultato.
Cliccando poi su "show", potrà visualizzare i dettagli inerenti alle prescrizioni del paziente.

**Stato del sistema al completamento:** non ci saranno variazioni allo stato del sistema.

### 5. __Medico - Gestione somministrazioni__

**Assunzione iniziale:** il medico ha il ruolo di prescrivere medicinali e analizzare le cartelle cliniche.


**Situazione normale:** il medico prima di visitare il paziente, accede al sistema tramite credenziali. 
Potrà cercare il paziente cliccando il tasto "Patient list" (in cui vedrà tutti i pazienti) oppure col tasto "View
Record" (in cui potrà, tramite barra di ricerca, cercare nome e/o cognome del paziente). In caso di pazienti omonimi,
potrà decidere di quale visualizzare i dati. In entrambi i menù, il medico potrà visualizzare il grado di pericolosità
del paziente e cliccare il tasto "show" sulla riga corrispondente al paziente da guardare, in modo da poter visualizzare
i dettagli delle precedenti prescrizioni (nel caso ce ne fossero).
Dopo aver visitato il paziente, per aggiungere la nuova prescrizione, dovrà cliccare su "new" nella riga corrispondente
al paziente. A conseguenza di ciò, apparirà un nuovo menù su cui il medico potrà insrire la data della visita, il 
medicinale prescritto, la dose, per quanti giorni ed eventuali note aggiuntive.
Cliccando su "invia", se tutto sarà corretto, ci sarà un messaggio di inserimento avvenuto con successo.
Il medico potrà quindi procedere con un nuovo inserimento, oppure navigare le altre sezioni del sito.

**Cosa può andare storto:** il medico inserisce una dose di medicinale maggiore rispetto al vincolo di sicurezza del
medicinale. In quel caso apparirà un messaggio d'errore che avvisa che la dose è troppo elevata.

**Stato del sistema al completamento:** una volta censita la prescrizione, questa sarà visibile a sistema a medici ed 
infermieri.

## QUALITY ASSURANCE

### Test Selection
Sono stati implementati i test di sistema inerenti a tutti gli scenari e a tutte le pagine web.
Si è cercato di coprire tutte le casistiche utente standard.
Ho dovuto utilizzare un Mock per censire i vari utenti:

| username  | password |
|-----------|----------|
| reception | 1234     |
| nurse     | 1        |
| doc       | 12       |
| manager   | 123      |

Nel Mock ho censito anche i medicinali.
Nonostante sia buona norma non farlo, ho voluto dare un ordine ai test, in modo da rispettare i vincoli di progetto di
avere un numero di test più ampio ed allo stesso momento non avere arbitrarietà di dati.
La scelta è causata dal fatto che il ruolo reception censisce i nuovi pazienti.

### Test coverage

E' stato raggiunto una coverage del 96% per le Istruzioni e l'82% dei branch.



