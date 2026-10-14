# CarDoc Web

Applicazione web per l'acquisto online di componenti e ricambi per auto, originali e compatibili. Gli utenti cercano i prodotti per parola chiave o per categoria, leggono le recensioni, usano carrello e wishlist, completano l'acquisto e consultano lo storico degli ordini. Gli amministratori gestiscono prodotti e utenti.

Progetto universitario per il corso di **Web Applications**, incentrato sullo sviluppo di un'applicazione web completa: il **backend** è implementato in **Java (Spring Boot)**, il **frontend** in **Angular** con HTML, CSS e TypeScript.

> Esiste anche la versione desktop del progetto: [CarDocDesktop](https://github.com/HackPietro/CarDocDesktop).

---

## Funzionalità

### Tutti i visitatori

- **Catalogo prodotti** nella home, con **ordinamento** per prezzo (crescente e decrescente), alfabetico (A-Z e Z-A), più venduti e meno venduti
- **Ricerca** per parola chiave e navigazione per **categoria**: ammortizzatori, freni, filtri, illuminazione, oli e liquidi, pneumatici, pulizia auto, sistema di scarico
- **Scheda prodotto** con foto, descrizione, prezzo, venditore, quantità selezionabile e **recensioni** degli utenti
- **Carrello**, utilizzabile anche prima di registrarsi, come previsto dalla documentazione
- **Registrazione** e **login**
- **Recupero password** tramite email
- Footer con i contatti e una **mappa interattiva** (Leaflet)

### Utente registrato

- **Wishlist** personale
- **Acquisto**: modulo con dati di spedizione (nome, cognome, paese, provincia, CAP, indirizzo) e dati della carta di pagamento
- **Profilo**: modifica dei dati personali
- **Storico ordini** con numero dell'ordine, quantità, importo totale e data, e vista di **dettaglio** di ogni ordine
- **Recensioni**: valutazione da 1 a 5 stelle con una descrizione

### Amministratore

- **Aggiunta di un prodotto**: ID, categoria, nome, descrizione, venditore, prezzo e foto, con compressione dell'immagine prima del caricamento e controllo sull'esistenza dell'ID
- **Rimozione di prodotti** e **rimozione di recensioni**
- **Pannello di controllo**, visibile solo agli amministratori, per **rimuovere un utente** e **cambiarne la tipologia** (`utente` o `admin`)

---

## Tecnologie

| Livello | Tecnologie |
|---|---|
| Frontend | Angular 17, Angular Material, TypeScript 5.2, RxJS, rendering lato server con Express |
| Librerie frontend | Leaflet (mappa), ngx-image-compress (compressione immagini) |
| Backend | Java 17, Spring Boot 3.2.1, Spring Web, Thymeleaf |
| Persistenza | PostgreSQL con accesso tramite pattern **DAO** su JDBC |
| Email | JavaMail |
| Altro | Lombok, Maven Wrapper |

---

## Architettura

```
┌──────────────────────┐   HTTP / JSON    ┌────────────────────────────┐
│  Frontend Angular    │ ───────────────► │  Backend Spring Boot       │
│  localhost:4200      │ ◄─────────────── │  localhost:8080            │
└──────────────────────┘                  │  Controller → Service → DAO│
         ▲                                └─────────────┬──────────────┘
         │ redirect con sessionId                       │ JDBC
         │                                              ▼
┌──────────────────────┐                  ┌────────────────────────────┐
│ Pagine login e       │                  │  PostgreSQL                │
│ registrazione        │                  │  localhost:5432            │
│ (Thymeleaf, backend) │                  └────────────────────────────┘
└──────────────────────┘
```

- Le pagine di **login e registrazione** sono template Thymeleaf servite dal backend. Dopo l'accesso, il backend crea una sessione HTTP e reindirizza l'utente al frontend Angular passando il `sessionId`, con cui il frontend recupera i dati dell'utente.
- Il backend segue una struttura a livelli: **Controller** (endpoint REST), **Service** (logica), **Persistence** con **DAO** per ogni entità e un `DBManager` che fornisce le connessioni.

---

## Struttura del repository

```
CarDocWeb/
├── CarDoc.pdf                              # Descrizione del progetto
├── backEnd/                                # Applicazione Spring Boot
│   ├── Database/                           # Dump PostgreSQL
│   └── src/main/
│       ├── java/com/example/backendcardoc/
│       │   ├── Controller/                 # Endpoint REST e login/registrazione
│       │   ├── Service/                    # Logica applicativa
│       │   └── Persistence/                # DBManager, interfacce DAO, implementazioni Postgres, modelli
│       └── resources/
│           ├── templates/                  # Pagine Thymeleaf di login e registrazione
│           └── static/                     # CSS e immagini delle pagine di accesso
└── frontEnd/                               # Applicazione Angular
    └── src/app/
        ├── componenti/                     # Pagine e componenti (home, prodotto, carrello, acquisto...)
        ├── Model/                          # Modelli TypeScript
        ├── Service/                        # Chiamate HTTP al backend
        └── auth/                           # Gestione dello stato di autenticazione
```

---

## Prerequisiti

- **Java 17**
- **Node.js** e **Angular CLI** (progetto Angular 17)
- **PostgreSQL** in esecuzione su `localhost:5432`
- Un account email con accesso SMTP, per l'invio dell'email di recupero password

---

## Avvio del progetto

1. Clona il repository:

   ```bash
   git clone https://github.com/HackPietro/CarDocWeb.git
   cd CarDocWeb
   ```

2. **Database**: ripristina il dump in `backEnd/Database/` su un database PostgreSQL.

   ```bash
   pg_restore -U <utente> -d <database> backEnd/Database/dump-postgres-202401171110.tar
   ```

3. **Backend**:

   ```bash
   cd backEnd
   ./mvnw spring-boot:run        # su Windows: mvnw.cmd spring-boot:run
   ```

   Il backend risponde su [http://localhost:8080](http://localhost:8080).

4. **Frontend**, in un altro terminale:

   ```bash
   cd frontEnd
   npm install
   ng serve
   ```

5. Apri [http://localhost:4200](http://localhost:4200).

> Gli indirizzi `localhost:4200` e `localhost:8080` sono usati direttamente nel codice, quindi i due servizi vanno avviati su queste porte.

---

## Configurazione

I parametri non si trovano in `application.properties`, ma direttamente nel codice del backend:

| Cosa | Dove | Note |
|---|---|---|
| Connessione a PostgreSQL | `Persistence/DBManager.java` | URL, utente e password del database |
| Account SMTP per le email | `Service/UtenteService.java`, metodo `sendEmail` | Mittente delle email di recupero password |

Prima di avviare il progetto adatta questi valori al tuo ambiente.

---

## API REST

Tutti gli endpoint sono sotto il prefisso `/api`, con CORS abilitato per `http://localhost:4200`.

| Risorsa | Endpoint principali |
|---|---|
| Prodotti `/api/prodotti` | `GET /findAll`, `/findCategoryProduct/{categoria}`, `/findSearchedProduct/{parola}`, `/getProduct/{id}`; `POST` (nuovo prodotto), `/addToCart`, `/addToWishlist`; `DELETE /{id}` |
| Immagini `/api/images` | `POST`, `GET /findByProductID/{id}` |
| Recensioni `/api/recensioni` | `POST`, `GET /findByProduct/{id}`, `DELETE /{id}` |
| Carrello `/api/cart` | `GET /findByCf/{cf}`, `/getCart/{cf}`, `/getQuantity/{cf}/{id}`; `DELETE /{cf}/{id}` e `/{cf}` |
| Wishlist `/api/wishlist` | `GET /findByCf/{cf}`, `/getWishlist/{cf}`; `DELETE /{cf}/{id}` e `/{cf}` |
| Ordini `/api/ordini` | `GET /getOrderById/{cf}`, `/findLastNumberOrder/{cf}`; `POST /insertOrder`, `/insertOrderDetail`, `/getDetailOrderByNumber` |
| Utenti `/api/utenti` | `POST`, `GET /{id}`, `/user-details`; `PUT /{cf}`; `DELETE /{cf}`; `POST /sendEmail`, `/setType` |
| Accesso | `POST /doLogin`, `POST /doRegistration`, `GET /registration` (pagine Thymeleaf) |

---

## Documentazione

Il file [`CarDoc.pdf`](./CarDoc.pdf) descrive scopo e funzionalità dell'applicazione: ricerca per parola chiave e per categoria, carrello e wishlist, area account con storico ordini, scheda prodotto con recensioni, funzioni di amministrazione e contatti per assistenza.

---

## Autori

**Pietro Cimino** · [@HackPietro](https://github.com/HackPietro), **Francesco Campagna**, **Cataldo Federico**, **Marco Picerno**
