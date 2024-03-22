db = db.getSiblingDB('RPS_DB');

// Создание коллекции players
db.createCollection("players");

// Создание коллекции lobbies
db.createCollection("lobbies");

// Создание коллекции games
db.createCollection("games");
