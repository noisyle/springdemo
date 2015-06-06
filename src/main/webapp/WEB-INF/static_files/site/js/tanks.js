EnemyTank = function (clientId, game, player, x, y) {
	this.x = x;
    this.y = y;
    this.rotation = 0;
    this.turret_rotation = 0;

    this.game = game;
    this.health = 10;
    this.player = player;
    this.alive = true;

    this.shadow = game.add.sprite(x, y, 'enemy', 'shadow');
    this.tank = game.add.sprite(x, y, 'enemy', 'tank1');
    this.turret = game.add.sprite(x, y, 'enemy', 'turret');

    this.shadow.anchor.set(0.5);
    this.tank.anchor.set(0.5);
    this.turret.anchor.set(0.3, 0.5);

    this.tank.name = clientId;
    game.physics.enable(this.tank, Phaser.Physics.ARCADE);
    this.tank.body.immovable = false;
    this.tank.body.collideWorldBounds = true;
    this.tank.body.bounce.setTo(1, 1);

    game.physics.arcade.velocityFromRotation(this.tank.rotation, 0, this.tank.body.velocity);
};

EnemyTank.prototype.update = function() {
    this.shadow.x = this.x;
    this.shadow.y = this.y;
    this.shadow.rotation = this.rotation;
    
    this.tank.x = this.x;
    this.tank.y = this.y;
    this.tank.rotation = this.rotation;

    this.turret.x = this.x;
    this.turret.y = this.y;
    this.turret.rotation = this.turret_rotation;
};


function preload () {
    game.load.atlas('tank', contextPath+'/static/site/image/tanks/tanks.png', contextPath+'/static/site/image/tanks/tanks.json');
    game.load.atlas('enemy', contextPath+'/static/site/image/tanks/enemy-tanks.png', contextPath+'/static/site/image/tanks/tanks.json');
    game.load.image('logo', contextPath+'/static/site/image/tanks/logo.png');
    game.load.image('bullet', contextPath+'/static/site/image/tanks/bullet.png');
    game.load.image('earth', contextPath+'/static/site/image/tanks/scorched_earth.png');
    game.load.spritesheet('kaboom', contextPath+'/static/site/image/tanks/explosion.png', 64, 64, 23);
}

function create () {
    //  Resize our game world to be a 2000 x 2000 square
    game.world.setBounds(0, 0, 800, 600);

    //  Our tiled scrolling background
    land = game.add.tileSprite(0, 0, 800, 600, 'earth');
    land.fixedToCamera = true;

    //  The base of our tank
    var x = game.world.randomX;
    var y = game.world.randomY;
    tank = game.add.sprite(x, y, 'tank', 'tank1');
    tank.anchor.setTo(0.5, 0.5);
    tank.animations.add('move', ['tank1', 'tank2', 'tank3', 'tank4', 'tank5', 'tank6'], 20, true);

    //  This will force it to decelerate and limit its speed
    game.physics.enable(tank, Phaser.Physics.ARCADE);
    tank.body.drag.set(0.2);
    tank.body.maxVelocity.setTo(400, 400);
    tank.body.collideWorldBounds = true;

    //  Finally the turret that we place on-top of the tank body
    turret = game.add.sprite(0, 0, 'tank', 'turret');
    turret.anchor.setTo(0.3, 0.5);

    //  Create some baddies to waste :)
    enemies = [];

    //  A shadow below our tank
    shadow = game.add.sprite(0, 0, 'tank', 'shadow');
    shadow.anchor.setTo(0.5, 0.5);

    tank.bringToTop();
    turret.bringToTop();

    game.camera.follow(tank);
    game.camera.deadzone = new Phaser.Rectangle(150, 150, 500, 300);
    game.camera.focusOnXY(0, 0);

    cursors = game.input.keyboard.createCursorKeys();
    
	cometd.batch(function(){
        // Publish on a service channel since the message is for the server only
    	onSocketConnected();
        cometd.subscribe('/tanks', eventHandler);
        cometd.subscribe('/tanks/echo', onEcho);
    });

}

//Function that manages the connection status with the Bayeux server
var _connected = false;
function _metaConnect(message) {
    if (cometd.isDisconnected()) {
        _connected = false;
        return;
    }
    _connected = message.successful === true;
}

// Function invoked when first contacting the server and
// when the server has lost the state of this client
function _metaHandshake(handshake) {
    if (handshake.successful === true) {
    	id = handshake.clientId;
    	if (!game) {
    		game = new Phaser.Game(800, 600, Phaser.AUTO, 'game', { preload: preload, create: create, update: update, render: render });
    	}
    }
}
// Socket connected
function onSocketConnected() {
    console.log("Connected to socket server");
    // Send local player data to the game server
    cometd.publish('/service/tanks', {type: 'new', x: tank.x, y:tank.y});
};
// Socket disconnected
function onSocketDisconnect() {
    console.log("Disconnected from socket server");
};

function onEcho(msg) {
	var clientList = msg.data;
	for(var i in clientList){
		enemies.push(new EnemyTank(clientList[i].id, game, tank, clientList[i].x, clientList[i].y));
	}
};
function eventHandler(msg) {
	switch (msg.data.type) {
	case 'new':
		onNewPlayer(msg);
		break;
	case 'move':
		onMovePlayer(msg);
		break;
	case 'remove':
		onRemovePlayer(msg);
		break;
	default:
		break;
	}
};
// New player
function onNewPlayer(msg) {
	if(id == msg.data.id) return;
	console.log("New player connected: "+msg.data.id);
	// Add new player to the remote players array
	enemies.push(new EnemyTank(msg.data.id, game, tank, msg.data.x, msg.data.y));
};
// Move player
function onMovePlayer(msg) {
	if(id == msg.data.id) return;
    var movePlayer = playerById(msg.data.id);
    // Player not found
    if (!movePlayer) {
        console.log("Player not found: "+msg.data.id);
        return;
    };
    // Update player position
    movePlayer.x = msg.data.x;
    movePlayer.y = msg.data.y;
    movePlayer.rotation = msg.data.rotation;
    movePlayer.turret_rotation = msg.data.turret_rotation;
};
// Remove player
function onRemovePlayer(msg) {
	if(id == msg.data.id) return;
    var removePlayer = playerById(msg.data.id);
    // Player not found
    if (!removePlayer) {
        console.log("Player not found: "+msg.data.id);
        return;
    };
    removePlayer.shadow.kill();
    removePlayer.tank.kill();
    removePlayer.turret.kill();
    // Remove player from array
    enemies.splice(enemies.indexOf(removePlayer), 1);
};
//Find player by ID
function playerById(id) {
    var i;
    for (i = 0; i < enemies.length; i++) {
        if (enemies[i].tank.name == id)
            return enemies[i];
    };
    return false;
};

function update () {
	for (var i = 0; i < enemies.length; i++) {
        if (enemies[i].alive) {
            game.physics.arcade.collide(tank, enemies[i].tank);
            enemies[i].update();
        }
    }
    if (cursors.left.isDown) {
        tank.angle -= 4;
    } else if (cursors.right.isDown) {
        tank.angle += 4;
    }

    if (cursors.up.isDown) {
        //  The speed we'll travel at
        currentSpeed = 300;
    } else {
        if (currentSpeed > 0) {
            currentSpeed -= 4;
        }
    }

    if (currentSpeed > 0) {
        game.physics.arcade.velocityFromRotation(tank.rotation, currentSpeed, tank.body.velocity);
    } else {
    	tank.body.velocity.x = tank.body.velocity.y = 0;
    }

    land.tilePosition.x = -game.camera.x;
    land.tilePosition.y = -game.camera.y;

    //  Position all the parts and align rotations
    shadow.x = tank.x;
    shadow.y = tank.y;
    shadow.rotation = tank.rotation;

    turret.x = tank.x;
    turret.y = tank.y;
    turret.rotation = game.physics.arcade.angleToPointer(turret);
    
    if(lastPos.x!=tank.x || lastPos.y!=tank.y || lastPos.rotation!=tank.rotation || lastPos.turret_rotation!=turret.rotation){
    	cometd.publish('/service/tanks', {type: 'move', id: id, x: tank.x, y:tank.y, rotation:tank.rotation, turret_rotation:turret.rotation});
    	lastPos = {x:tank.x,y:tank.y,rotation:tank.rotation,turret_rotation:turret.rotation};
    }
}

function render () {

}

$(window).unload(function(){
    cometd.publish('/service/tanks', {type: 'remove', id: id});
    cometd.disconnect(true);
});


var game;
var land;

var id;
var shadow;
var tank;
var turret;
var lastPos = {x:0,y:0,rotation:0,turret_rotation:0};

var enemies;

var currentSpeed = 0;
var cursors;


var cometd = $.cometd;
cometd.configure({
    url: location.protocol + "//" + location.host + contextPath + "/cometd"
});
cometd.addListener('/meta/handshake', _metaHandshake);
cometd.addListener('/meta/connect', _metaConnect);
cometd.handshake();

