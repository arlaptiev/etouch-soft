// Dialogflow fulfillment getting started guide:
// https://dialogflow.com/docs/how-tos/getting-started-fulfillment

'use strict';

const functions = require('firebase-functions');
const {WebhookClient} = require('dialogflow-fulfillment');
const {Card, Suggestion} = require('dialogflow-fulfillment');


// ToDo turn into database
const imgLinks = {
  "Harry Potter": "https://images-na.ssl-images-amazon.com/images/I/51oSZOESQ6L.jpg",
  "1984": "https://images-na.ssl-images-amazon.com/images/I/41E9Z5XaHcL.jpg",
  "Fahrenheit 451": "https://images-na.ssl-images-amazon.com/images/I/81v%2BjygSh2L.jpg",
  "Anna Karenina": "https://www.thoughtco.com/thmb/Lc7CZWNLM55tIRkz5pak10qpL4U=/768x0/filters:no_upscale():max_bytes(150000):strip_icc()/anna-karenina-59ce5f876f53ba001172c6c8.jpg",
  "Elon Musk": "https://images-na.ssl-images-amazon.com/images/I/81cdxHMnmmL.jpg"
};


process.env.DEBUG = 'dialogflow:debug'; // enables lib debugging statements

exports.dialogflowFirebaseFulfillment = functions.https.onRequest((request, response) => {
  const agent = new WebhookClient({ request, response });
  console.log('Dialogflow Request headers: ' + JSON.stringify(request.headers));
  console.log('Dialogflow Request body: ' + JSON.stringify(request.body));

  function welcome (agent) {
    agent.add(`Welcome eTouch Assistant!`);
  }

  function fallback (agent) {
    agent.add(`I didn't understand`);
    agent.add(`I'm sorry, can you try again?`);
  }

  // Uncomment and edit to make your own intent handler
  // uncomment `intentMap.set('your intent name here', yourFunctionHandler);`
  // below to get this function to be run when a Dialogflow intent is matched
  function findBook(agent) {
    // agent.add(`Some text on top`);
    const title = agent.parameters.books;
    const url = title.replace(" ", "-");

    agent.add(new Card({
        title: `📕 ` + title,
        imageUrl: imgLinks[title],
        text: `Follow this link to open the book in your eTouch application`,
        buttonText: 'Read',
        buttonUrl: 'http://etouchprojectreading.com/?book=' + url
      })
    );
    //agent.add(new Suggestion(`Harry Potter`));
    //agent.add(new Suggestion(`Suggestion`));
    //agent.setContext({ name: 'books', lifespan: 2, parameters: { city: 'New York' }});
  }
  /*
  // Uncomment and edit to make your own Google Assistant intent handler
  // uncomment `intentMap.set('your intent name here', googleAssistantHandler);`
  // below to get this function to be run when a Dialogflow intent is matched
  function googleAssistantHandler(agent) {
    let conv = agent.conv(); // Get Actions on Google library conv instance
    conv.ask('Hello from the Actions on Google client library!'); // Use Actions on Google library
    agent.add(conv); // Add Actions on Google library responses to your agent's response
  }
  */

  // Run the proper function handler based on the matched Dialogflow intent name
  let intentMap = new Map();
  intentMap.set('Default Welcome Intent', welcome);
  intentMap.set('Default Fallback Intent', fallback);
  intentMap.set('findBook', findBook);
  // intentMap.set('<INTENT_NAME_HERE>', googleAssistantHandler);
  agent.handleRequest(intentMap);
});
