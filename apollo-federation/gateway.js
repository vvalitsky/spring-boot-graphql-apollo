const { ApolloServer } = require("apollo-server");
const { ApolloGateway } = require("@apollo/gateway");
const serviceList = [];

const config = {
    local: {
        spring_boot_apollo: {
            url: "http://localhost:5150/graphql",
            create: true
        }
    }
};

Object.keys(config[process.env.NODE_ENV]).map(function (key) {
    const service = config[process.env.NODE_ENV][key];
    if (service.create) {
        serviceList.push({
            name: key, url: service.url
        })
    }
});

const gateway = new ApolloGateway({
    serviceList: serviceList
});

(async () => {
    const {schema, executor} = await gateway.load();
    new ApolloServer({
        schema, executor, context: ({req}) => ({
            userId: req.headers.authorization
        })
    }).listen({port: 6505}).then(({url}) => {
        console.log(`Server started on ${url}`);
    });
})();
