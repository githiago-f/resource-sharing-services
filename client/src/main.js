import './style.css';
import { serviceUrl } from './data/config';
import { graphql } from './data/workspace.graphql';
import { workspaceListItem } from './view/workspace';

let workspaces = [];

async function renderHome() {
    if(workspaces.length === 0) {
        const { workspacesByUser } = await graphql('homePage', { page: 0 });
        if(workspacesByUser === null || typeof workspacesByUser !== 'object') {
            emptyList();
            return;
        }
        workspaces = workspacesByUser;
    }

    document.querySelector('#workspaces').innerHTML =
        workspaces.map(workspaceListItem).join('\n');
}

async function createWorkspace() {
    const { workspace } = await graphql('createWorkspace', {
        image: 'openjdk:latest',
        params: '-v .:/usr/java'
    });
    workspaces.push(workspace);
    renderHome();
}

function showControls() {
    document.getElementById('refresh').style.display = 'block';
    document.getElementById('create').style.display = 'block';
    document.getElementById('create').onclick = createWorkspace;
    document.getElementById('refresh').onclick = () => {
        workspaces = [];
        renderHome();
    }
}

function emptyList() {
    document.querySelector('#workspaces').innerHTML =
                '<li>No workspace found</li>';
}

document.getElementById('gen_auth').onclick = () => {
    const url = serviceUrl();
    url.pathname += '/login';
    fetch(url).then(res=>res.text()).then(token => {
        window.localStorage.setItem('token', token);
        renderHome();
        showControls();
    });
}

emptyList();
